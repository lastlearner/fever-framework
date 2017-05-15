package com.github.fanfever.fever.upload.storage.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.github.fanfever.fever.upload.storage.StorageCallback;

/**
 * @author scott.he
 * @date 2017/4/7
 */
public interface S3StorageCallback<T> extends StorageCallback<AmazonS3, T> {
  T execute(AmazonS3 amazonS3);
}
