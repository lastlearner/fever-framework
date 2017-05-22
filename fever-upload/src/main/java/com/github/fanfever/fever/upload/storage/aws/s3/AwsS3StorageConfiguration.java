package com.github.fanfever.fever.upload.storage.aws.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.github.fanfever.fever.upload.model.ResponseModel;
import com.github.fanfever.fever.upload.util.PreCheck;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * aws.s3 存储
 * @author scott.he
 * @date 2017/4/8
 */
@Component
public class AwsS3StorageConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(AwsS3StorageConfiguration.class);
  //region Properties
  @Value("${cloud.aws.region.static}")
  private String region;
  @Value("${cloud.aws.region.auto}")
  private String endpointAuto;
  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;
  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;
  //endregion

  //region AmazonS3
  private AmazonS3 amazonS3() {
    AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    clientConfiguration.setProtocol(Protocol.HTTP);
    AmazonS3 amazonS3 = new AmazonS3Client(awsCredentials, clientConfiguration);
    amazonS3.setRegion(RegionUtils.getRegion(region));
    return amazonS3;
  }
  //endregion

  /**
   * 下载
   * @param bucket 存储空间
   * @param key 文件名
   * @return
   */
  public ResponseModel download(final String bucket, final String key) {
    return execute(new S3StorageCallback<ResponseModel>() {
      public ResponseModel execute(AmazonS3 amazonS3) {
        boolean exist = amazonS3.doesObjectExist(bucket, key);
        if(!exist)
          return new ResponseModel(false, ResponseModel.Operation.DOWNLOAD);
        S3Object s3Object = amazonS3.getObject(bucket, key);
        return new ResponseModel(s3Object.getObjectContent(),
            true,
            s3Object.getObjectMetadata().getUserMetadata(),
            s3Object.getObjectMetadata().getRawMetadata(), ResponseModel.Operation.DOWNLOAD);
      }
    });
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param key 文件名
   * @param inputStream 流
   * @param objectMetadata 元数据
   * @return
   */
  public ResponseModel upload(final String bucket, final String key, final InputStream inputStream, final ObjectMetadata
      objectMetadata) {
    return execute(new S3StorageCallback<ResponseModel>() {
      public ResponseModel execute(AmazonS3 amazonS3) {
        amazonS3.putObject(bucket, key, inputStream, objectMetadata);
        return new ResponseModel(false, ResponseModel.Operation.UPLOAD);
      }
    });
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param multipartFiles 文件列表
   * @return
   */
  public ResponseModel upload(final String bucket, final MultipartFile[] multipartFiles) {
    return execute(new S3StorageCallback<ResponseModel>() {
      public ResponseModel execute(AmazonS3 amazonS3) {
        try {
          for (MultipartFile file : multipartFiles) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            initializeMetadata(objectMetadata, file);
            amazonS3.putObject(bucket, file.getOriginalFilename(), file.getInputStream(), objectMetadata);
          }
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
          throw new RuntimeException(e);
        }
        return new ResponseModel(false, ResponseModel.Operation.UPLOAD);
      }
    });
  }

  /**
   * S3 Tokens Info
   * @return
   */
  public Map<String, String> tokens() {
    return MapUtils.putAll(new HashMap<String, String>(), new String[] {
        "accessKey", accessKey,
        "secretKey", secretKey,
        "region", region
    });
  }

  /**
   * 获取指定对象的外链地址.
   * @param bucket 存储空间
   * @param key 对象名称
   * @return
   */
  public String chain(final String bucket, final String key) {
    return execute(new S3StorageCallback<String>() {
      public String execute(AmazonS3 amazonS3) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest
            (bucket, key);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url == null ? "" : url.toString();
      }
    });
  }

  public <T> T execute(S3StorageCallback<T> action) {
    PreCheck.notNull(action);
    AmazonS3 amazonS3 = this.amazonS3();
    try {
      return action.execute(amazonS3);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
    }
  }

  /**
   * 元数据
   * @param objectMetadata
   * @param multipartFile
   */
  private void initializeMetadata(ObjectMetadata objectMetadata, MultipartFile multipartFile) {
    //region 元数据-用户自定义UserMetadata
    objectMetadata.addUserMetadata("name", multipartFile.getName());
    objectMetadata.addUserMetadata("contentType", multipartFile.getContentType());
    objectMetadata.addUserMetadata("originalFilename", multipartFile.getOriginalFilename());
    objectMetadata.addUserMetadata("size", String.valueOf(multipartFile.getSize()));
    //endregion

    //region 元数据-非用户自定义Metadata
    objectMetadata.setContentType(multipartFile.getContentType());
    //endregion

    //region ContentLength
    objectMetadata.setContentLength(multipartFile.getSize());
    //endregion
  }
}
