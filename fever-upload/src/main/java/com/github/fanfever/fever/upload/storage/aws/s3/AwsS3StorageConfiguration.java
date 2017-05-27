package com.github.fanfever.fever.upload.storage.aws.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.amazonaws.util.BinaryUtils;
import com.github.fanfever.fever.upload.model.ResponseModel;
import com.github.fanfever.fever.upload.util.PreCheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * aws.s3 存储
 * @author scott.he
 * @date 2017/4/8
 */
@Slf4j
@Component
public class AwsS3StorageConfiguration {
  //region Properties
  @Value("${cloud.aws.region.static}")
  private String region;
  @Value("${cloud.aws.region.auto}")
  private String endpointAuto;
  @Value("${cloud.aws.credentials.accessKey}")
  private String accessKey;
  @Value("${cloud.aws.credentials.secretKey}")
  private String secretKey;
  @Value("${cloud.aws.s3.bucketName}")
  private String bucket;
  @Value("${cloud.aws.s3.keyPrefix}")
  private String keyPrefix;
  //endregion

  //region AmazonS3
  private AmazonS3 amazonS3() {
    AmazonS3 amazonS3 = null;
    if (isDevelopProfile()) {
      AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
      ClientConfiguration clientConfiguration = new ClientConfiguration();
      clientConfiguration.setProtocol(Protocol.HTTPS);
      amazonS3 = new AmazonS3Client(awsCredentials, clientConfiguration);
    } else {
      amazonS3 = new AmazonS3Client(new InstanceProfileCredentialsProvider());
    }
    amazonS3.setRegion(RegionUtils.getRegion(region));
    return amazonS3;
  }
  //endregion

  //region AWSSecurityTokenServiceClient
  private AWSSecurityTokenServiceClient awsSecurityTokenServiceClient() {
    AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    clientConfiguration.setProtocol(Protocol.HTTPS);
    AWSSecurityTokenServiceClient awsSecurityTokenServiceClient =
        new AWSSecurityTokenServiceClient(awsCredentials, clientConfiguration);
    awsSecurityTokenServiceClient.setRegion(RegionUtils.getRegion(region));
    return awsSecurityTokenServiceClient;
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
    return upload(bucket, key, inputStream, objectMetadata, null);
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param key 对象
   * @param inputStream 流
   * @param objectMetadata 元数据
   * @param cannedAccessControlList 权限
   * @return
   */
  public ResponseModel upload(final String bucket, final String key, final InputStream inputStream, final ObjectMetadata
      objectMetadata, final CannedAccessControlList cannedAccessControlList) {
    return execute(new S3StorageCallback<ResponseModel>() {
      public ResponseModel execute(AmazonS3 amazonS3) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, inputStream,
            objectMetadata);
        if(cannedAccessControlList != null)
          putObjectRequest.withCannedAcl(cannedAccessControlList);
        amazonS3.putObject(putObjectRequest);
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
    return upload(bucket, multipartFiles, null);
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param multipartFiles 文件列表
   * @param cannedAccessControlList 权限
   * @return
   */
  public ResponseModel upload(final String bucket, final MultipartFile[] multipartFiles,
                              CannedAccessControlList cannedAccessControlList) {
    return upload(bucket, multipartFiles, cannedAccessControlList, "multipart/form-data");
  }

  /**
   * 上传
   * @param bucket 存储空间
   * @param multipartFiles 文件列表
   * @param cannedAccessControlList 权限
   * @param contentType
   * @return
   */
  public ResponseModel upload(final String bucket, final MultipartFile[] multipartFiles,
                              final CannedAccessControlList cannedAccessControlList, final String
                                  contentType) {
    return execute(new S3StorageCallback<ResponseModel>() {
      public ResponseModel execute(AmazonS3 amazonS3) {
        try {
          ObjectMetadata objectMetadata = new ObjectMetadata();
          if(contentType != null) {
            objectMetadata.setContentType(contentType);
          }
          for (MultipartFile file : multipartFiles) {
            objectMetadata.setContentLength(file.getSize());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, file
                .getOriginalFilename(), file.getInputStream(), objectMetadata);
            if(cannedAccessControlList != null)
              putObjectRequest.withCannedAcl(cannedAccessControlList);
            amazonS3.putObject(putObjectRequest);
          }
        } catch (IOException e) {
          log.error(e.getMessage(), e);
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
    try {
      long expireEndTime = System.currentTimeMillis() + 60 * 1000 * 30;
      Date expiration = new Date(expireEndTime);
      String postPolicy = "{\n" +
          "  \"expiration\": " + expiration.toString() + ",\n" +
          "  \"conditions\": [ \n" +
          "    {\"bucket\": " + bucket + "}, \n" +
          "    [\"starts-with\", \"$key\", \"" + keyPrefix + "\"],\n" +
          "    {\"acl\": \"public-read\"},\n" +
          "    [\"starts-with\", \"$Content-Type\", \"\"],\n" +
          "    [\"content-length-range\", 0, 1048576000]\n" +
          "  ]\n" +
          "}";
      String policy = BinaryUtils.toBase64(postPolicy.getBytes("utf-8"));
      Mac sha1HMAC = Mac.getInstance("HmacSHA1");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("utf-8"),
          "HmacSHA1");
      sha1HMAC.init(secretKeySpec);
      String signature = BinaryUtils.toBase64(sha1HMAC.doFinal(policy.getBytes("utf-8")));
      return MapUtils.putAll(new HashMap<String, String>(), new String[] {
          "accessKey", accessKey,
          "policy", policy,
          "signature", signature,
          "bucket", bucket,
          "region", region,
          "dir", keyPrefix
      });
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage(), e);
    } catch (NoSuchAlgorithmException e) {
      log.error(e.getMessage(), e);
    } catch (InvalidKeyException e) {
      log.error(e.getMessage(), e);
    }
    return MapUtils.putAll(new HashMap<String, String>(), new String[] {});
  }

  /**
   * S3 Temporary Tokens Info.
   * <p>AWS Security Token Service -> STS
   * <P>Token Vending Machine -> TVM
   *
   * More-info: <a href="https://aws.amazon.com/cn/blogs/china/token-vending-machine/">Token Vending Machine</a>
   *
   * @return
   */
  public Map<String, String> temporaryTokens() {
    GetSessionTokenRequest sessionTokenRequest = new GetSessionTokenRequest();
    sessionTokenRequest.setDurationSeconds(900); //15 minutes
    AWSSecurityTokenServiceClient awsSecurityTokenServiceClient = this
        .awsSecurityTokenServiceClient();
    GetSessionTokenResult sessionTokenResult =awsSecurityTokenServiceClient.getSessionToken(sessionTokenRequest);
    Credentials sessionCredentials = sessionTokenResult.getCredentials();

    return MapUtils.putAll(new HashMap<String, String>(), new String[] {
        "accessKeyId", sessionCredentials.getAccessKeyId(),
        "secretAccessKey", sessionCredentials.getSecretAccessKey(),
        "sessionToken", sessionCredentials.getSessionToken(),
        "bucket", bucket,
        "dir", keyPrefix,
        "region", region
    });
  }

  /**
   * 获取指定对象的临时外链地址.
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

  /**
   * 上传-完成后返回外链. 默认公共读
   * @param bucket 存储空间
   * @param key 对象名称
   * @param inputStream 流
   * @param objectMetadata 元数据
   * @return
   */
  public String uploadedReturnChain(final String bucket, final String key, final InputStream inputStream, final ObjectMetadata
      objectMetadata) {
    return execute(new S3StorageCallback<String>() {
        public String execute(AmazonS3 amazonS3) {
          upload(bucket, key, inputStream, objectMetadata, CannedAccessControlList.PublicRead);
          return defaultAddress(bucket, key);
        }
    });
  }

  /**
   * 生成预签名
   * @param bucket 存储空间
   * @param key 对象名称
   * @return
   */
  public Map<String, String> genPreSignedUrl(final String bucket, final String key, final String
      httpMethod) {
    return execute(new S3StorageCallback<Map<String, String>>() {
      public Map<String, String> execute(AmazonS3 amazonS3) {
        java.util.Date expiration = new java.util.Date();
        long msec = expiration.getTime();
        msec += 1000 * 60 * 10; // Add 10 minutes
        expiration.setTime(msec);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, key);
        generatePresignedUrlRequest.setExpiration(expiration);

        String method = new String(httpMethod);
        HttpMethod methodType;
        if("get".equalsIgnoreCase(httpMethod)) {
          methodType = HttpMethod.GET;
        } else if("put".equalsIgnoreCase(httpMethod)) {
          methodType = HttpMethod.PUT;
        } else if("post".equalsIgnoreCase(httpMethod)) {
          methodType = HttpMethod.POST;
        } else if("head".equalsIgnoreCase(httpMethod)) {
          methodType = HttpMethod.HEAD;
        } else if("delete".equalsIgnoreCase(httpMethod)) {
          methodType = HttpMethod.DELETE;
        } else if("patch".equalsIgnoreCase(httpMethod)) {
          methodType = HttpMethod.PATCH;
        } else {
          //method不识别, 默认get
          method = "get";
          methodType = HttpMethod.GET;
        }
        generatePresignedUrlRequest.setMethod(methodType);
        generatePresignedUrlRequest.addRequestParameter(Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString());
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

        return MapUtils.putAll(new HashMap<String, String>(), new String[] {
            "url", (url == null ? "" : url.toString()),
            "method", method,
            "bucket", bucket,
            "region", region,
            "path", key
        });
      }
    });
  }

  /**
   *
   * @param bucket
   * @param key
   * @return
   */
  public Boolean deleteObject(final String bucket, final String key) {
    return execute(new S3StorageCallback<Boolean>() {
      public Boolean execute(AmazonS3 amazonS3) {
        try {
          boolean exists = amazonS3.doesObjectExist(bucket, key);
          if (exists) {
            amazonS3.deleteObject(bucket, key);
          }
          return true;
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
        return false;
      }
    });
  }

  /**
   * 默认文件外链地址
   * @param bucket
   * @param key
   * @return
   */
  public String defaultAddress(String bucket, String key) {
    return "https://s3." + region + ".amazonaws.com.cn/" + bucket + "/" + key;
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

  @Autowired
  private Environment environment;
  private boolean isDevelopProfile() {
    String[] profiles = environment.getActiveProfiles();
    if (profiles == null || profiles.length == 0)
      return true;
    return "develop".equals(profiles[0]) || "frontend".equals(profiles[0]);
  }
}
