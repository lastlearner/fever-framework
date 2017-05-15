package com.github.fanfever.fever.upload.storage;

import com.github.fanfever.fever.upload.storage.aliyun.oss.OSSStorageCallback;
import com.github.fanfever.fever.upload.storage.aws.s3.S3StorageCallback;

/**
 * @author scott.he
 * @date 2017/4/7
 */
public interface Storage {

  <T> T execute(OSSStorageCallback<T> action) throws Exception;

  <T> T execute(S3StorageCallback<T> action) throws Exception;
}
