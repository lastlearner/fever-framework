package com.github.fanfever.fever.upload.storage;

/**
 * @author scott.he
 * @date 2017/4/7
 */
public interface StorageCallback<S, T> {
  T execute(S s);
}
