package com.github.fanfever.fever.upload.storage.aliyun.oss;

import com.aliyun.oss.OSSClient;
import com.github.fanfever.fever.upload.storage.StorageCallback;

/**
 * @author scott.he
 * @date 2017/4/7
 */
public interface OSSStorageCallback<T> extends StorageCallback<OSSClient, T> {
  T execute(OSSClient ossClient);
}
