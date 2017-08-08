package com.github.fanfever.fever.upload.storage.aliyun.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.comm.Protocol;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.github.fanfever.fever.upload.model.DownloadRequest;
import com.github.fanfever.fever.upload.model.DownloadResponse;
import com.github.fanfever.fever.upload.model.UploadRequest;
import com.github.fanfever.fever.upload.model.UploadResponse;
import com.github.fanfever.fever.upload.util.PreCheck;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * aliyun.oss 存储
 * @author scott.he
 * @date 2017/4/8
 */
@Slf4j
@Component
@Lazy
public class OSSStorageConfiguration {
  //region Properties
  @Value("${oss.protocol}")
  private String protocol;
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
    return ossClient(endpoint);
  }

  private OSSClient ossClient(String endpoint) {
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    if ("https".equals(protocol)) {
      clientConfiguration.setProtocol(Protocol.HTTPS);
    }
    return new OSSClient(endpoint, accessKeyId, accessKeySecret, clientConfiguration);
  }
  //endregion

  /**
   * 下载
   * @param bucket 存储桶
   * @param key 对象名
   * @return {@link DownloadResponse}
   */
  public DownloadResponse download(final String bucket, final String key) {
    return execute(ossClient -> {
      boolean exists = ossClient.doesObjectExist(bucket, key);
      if (!exists)
        return new DownloadResponse().withExists(false);

      OSSObject object = ossClient.getObject(bucket, key);

      return DownloadResponse.builder()
          .bucketName(bucket)
          .keyName(key)
          .exists(true)
          .inputStream(object.getObjectContent())
          .userMetadata(object.getObjectMetadata().getUserMetadata())
          .metadata(object.getObjectMetadata().getRawMetadata())
          .status(DownloadResponse.DownStatus.SUCCESS).build();
    });
  }

  /**
   * 下载
   * @param downloadRequest downloadRequest
   * @return {@link DownloadResponse}
   */
  public DownloadResponse download(DownloadRequest downloadRequest) {
    return download(downloadRequest.getBucketName(), downloadRequest.getKeyName());
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param key 对象名
   * @param inputStream 流
   * @return {@link UploadResponse}
   */
  public UploadResponse upload(final String bucket, final String key, final InputStream inputStream) {
    return execute(ossClient -> {
      ossClient.putObject(bucket, key, inputStream);
      return UploadResponse.builder()
          .bucketName(bucket)
          .keyName(key)
          .status(UploadResponse.UpStatus.SUCCESS).build();
    });
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param key 对象名
   * @param inputStream 流
   * @param objectMetadata 元数据
   * @return {@link UploadResponse}
   */
  public UploadResponse upload(final String bucket, final String key, final InputStream inputStream, final ObjectMetadata
      objectMetadata) {
    return execute(ossClient -> {
      ossClient.putObject(bucket, key, inputStream, objectMetadata);
      return UploadResponse.builder()
          .bucketName(bucket)
          .keyName(key)
          .status(UploadResponse.UpStatus.SUCCESS).build();
    });
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param multipartFiles 文件列表
   * @return {@link java.util.List<UploadResponse>}
   */
  public List<UploadResponse> upload(final String bucket, final MultipartFile[] multipartFiles) {
    return execute(ossClient -> {
      List<UploadResponse> uploadResponseList = Lists.newArrayList();
      for (MultipartFile file : multipartFiles) {
        UploadResponse uploadResponse = UploadResponse.builder()
            .bucketName(bucket)
            .keyName(file.getOriginalFilename())
            .status(UploadResponse.UpStatus.SUCCESS).build();
        try {
          ossClient.putObject(bucket, file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
          log.error(e.getMessage(), e);
          uploadResponse.setStatus(UploadResponse.UpStatus.FAILED);
          throw new RuntimeException(e);
        }
        uploadResponseList.add(uploadResponse);
      }
      return uploadResponseList;
    });
  }

  /**
   * 上传
   * @param uploadRequest uploadRequest
   * @return {@link UploadResponse}
   */
  public UploadResponse upload(UploadRequest uploadRequest) {
    return execute(ossClient -> {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setUserMetadata(uploadRequest.getUserMetadata());
      ossClient.putObject(uploadRequest.getBucketName(), uploadRequest.getKeyName(),
          uploadRequest.getInputStream(), objectMetadata);
      return UploadResponse.builder()
          .bucketName(uploadRequest.getBucketName())
          .keyName(uploadRequest.getKeyName())
          .status(UploadResponse.UpStatus.SUCCESS).build();
    });
  }

  /**
   * 上传
   * @param uploadRequestList uploadRequestList
   * @return {@link List<UploadResponse>}
   */
  public List<UploadResponse> upload(List<UploadRequest> uploadRequestList) {
    return execute(ossClient -> {
      List<UploadResponse> uploadResponseList = Lists.newArrayList();
      uploadRequestList.forEach(uploadRequest -> {
        UploadResponse uploadResponse = UploadResponse.builder()
            .bucketName(uploadRequest.getBucketName())
            .keyName(uploadRequest.getKeyName())
            .status(UploadResponse.UpStatus.SUCCESS).build();
        try {
          ObjectMetadata objectMetadata = new ObjectMetadata();
          objectMetadata.setUserMetadata(uploadRequest.getUserMetadata());
          ossClient.putObject(uploadRequest.getBucketName(), uploadRequest.getKeyName(),
              uploadRequest.getInputStream(), objectMetadata);
        } catch (Exception e) {
          log.error(e.getMessage(), e);
          uploadResponse.setStatus(UploadResponse.UpStatus.FAILED);
        }
        uploadResponseList.add(uploadResponse);
      });
      return uploadResponseList;
    });
  }

  /**
   * OSS Tokens Info
   * @param dir 文件夹
   */
  public Map<String, String> tokens(final String dir) {
    return tokens(dir, bucketName, endpoint);
  }

  /**
   * OSS Tokens Info (其中, customBucket - customEndpoint 一一对应, 否则生成的token不可用)
   * @param dir 文件夹
   * @param customBucket 自定义bucket
   * @param customEndpoint 自定义endpoint
   */
  public Map<String, String> tokens(final String dir, final String customBucket, final String customEndpoint) {
    String host = protocol + "://" + customBucket + "." + customEndpoint;
    OSSClient client = this.ossClient(customEndpoint);
    try {
      long expireTime = 30;
      long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
      Date expiration = new Date(expireEndTime);
      PolicyConditions policyConditions = new PolicyConditions();
      policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
      policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

      String postPolicy = client.generatePostPolicy(expiration, policyConditions);
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
      log.error(e.getMessage());
      throw new RuntimeException(e);
    } finally {
      if(client != null) {
        client.shutdown();
      }
    }
  }

  /**
   * 获取指定对象的外链地址.
   *
   * 格式: https:// + bucket + "." + endpoint + "/" + key
   * @param bucket 存储空间
   * @param key 对象名称
   * @return
   */
  public String chain(String bucket, String key) {
    return new StringBuffer()
        .append(protocol)
        .append("://")
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

  public <T> T execute(String endpoint, OSSStorageCallback<T> action) {
    PreCheck.notNull(action);
    OSSClient ossClient = this.ossClient(endpoint);
    try {
      return action.execute(ossClient);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if(ossClient != null)
        ossClient.shutdown();
    }
  }

}
