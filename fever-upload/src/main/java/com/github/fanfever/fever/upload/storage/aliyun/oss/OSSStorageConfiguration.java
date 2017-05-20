package com.github.fanfever.fever.upload.storage.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.github.fanfever.fever.upload.annotation.BeingRealization;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * aliyun.oss 存储
 * @author scott.he
 * @date 2017/4/8
 */
@Component
public class OSSStorageConfiguration {
  private Logger logger = LoggerFactory.getLogger(OSSStorageConfiguration.class);
  //region Properties
  @Value("${oss.endpoint}")
  private String endpoint;
  @Value("${oss.accessKeyId}")
  private String accessKeyId;
  @Value("${oss.accessKeySecret}")
  private String accessKeySecret;
  @Value("${oss.bucketName}")
  private String bucketName;
  //endregion

  //region OSSClient
  private OSSClient ossClient() {
    return new OSSClient(endpoint, accessKeyId, accessKeySecret);
  }
  //endregion

  /**
   * 下载
   * @param bucket 存储空间
   * @param key 对象名
   * @return
   */
  public ResponseModel download(final String bucket, final String key) {
    return execute(new OSSStorageCallback<ResponseModel>() {
      public ResponseModel execute(OSSClient ossClient) {
        boolean exists = ossClient.doesObjectExist(bucket, key);
        if(!exists)
          return new ResponseModel(false, ResponseModel.Operation.DOWNLOAD);

        OSSObject object = ossClient.getObject(bucket, key);
        return new ResponseModel(object.getObjectContent(),
                true,
                object.getObjectMetadata().getUserMetadata(),
                object.getObjectMetadata().getRawMetadata(), ResponseModel.Operation.DOWNLOAD);
      }
    });
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param key 对象名
   * @param inputStream 流
   * @return
   */
  public ResponseModel upload(final String bucket, final String key, final InputStream inputStream) {
    return execute(new OSSStorageCallback<ResponseModel>() {
      public ResponseModel execute(OSSClient ossClient) {
        ossClient.putObject(bucket, key, inputStream);
        return new ResponseModel(false, ResponseModel.Operation.UPLOAD);
      }
    });
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param key 对象名
   * @param inputStream 流
   * @param objectMetadata 元数据
   * @return
   */
  public ResponseModel upload(final String bucket, final String key, final InputStream inputStream, final ObjectMetadata
          objectMetadata) {
    return execute(new OSSStorageCallback<ResponseModel>() {
      public ResponseModel execute(OSSClient ossClient) {
        ossClient.putObject(bucket, key, inputStream, objectMetadata);
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
    return execute(new OSSStorageCallback<ResponseModel>() {
      public ResponseModel execute(OSSClient ossClient) {
        for(MultipartFile file : multipartFiles) {
          ObjectMetadata objectMetadata = new ObjectMetadata();
          initializeMetadata(objectMetadata, file);
          try {
            ossClient.putObject(bucket, file.getOriginalFilename(), file.getInputStream(),
                    objectMetadata);
          } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
          }
        }
        return new ResponseModel(false, ResponseModel.Operation.UPLOAD);
      }
    });
  }

  /**
   * OSS Tokens Info
   * @return
   */
  public Map<String, String> tokens(final String dir) {
    String host = "http://" + bucketName + "." + endpoint;
    OSSClient client = this.ossClient();
    try {
      long expireTime = 30;
      long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
      Date expiration = new Date(expireEndTime);
      PolicyConditions policyConds = new PolicyConditions();
      policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
      policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

      String postPolicy = client.generatePostPolicy(expiration, policyConds);
      byte[] binaryData = postPolicy.getBytes("utf-8");
      String encodedPolicy = BinaryUtil.toBase64String(binaryData);
      String postSignature = client.calculatePostSignature(postPolicy);

      return MapUtils.putAll(new HashMap<String, String>(), new String[] {
              "accessId", accessKeyId,
              "policy", encodedPolicy,
              "signature", postSignature,
              "expire", String.valueOf(expireEndTime / 1000),
              "dir", dir,
              "host", host
      });
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      if(client != null) {
        client.shutdown();
      }
    }
    //return MapUtils.putAll(new HashMap<String, String>(), new String[] {});
  }

  /**
   * 获取指定对象的外链地址.
   *
   * 格式: bucket + "." + endpoint + "/" + key
   * @param bucket 存储空间
   * @param key 对象名称
   * @return
   */
  public String chain(String bucket, String key) {
    return new StringBuffer()
            .append(bucket)
            .append(".")
            .append(endpoint)
            .append("/")
            .append(key).toString();
  }

  public <T> T execute(OSSStorageCallback<T> action) {
    PreCheck.notNull(action);
    OSSClient ossClient = this.ossClient();
    try {
      return action.execute(ossClient);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      /*if(ossClient != null)
        ossClient.shutdown();*/
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
  }

}
