#fever-upload
通用上传下载(详细使用可参考`UniversalOssController`、`UniversalAwsS3Controller`)

#Example / Usage
* OSS
    上传:
    `ResponseModel responseModel = ossStorageConfiguration.upload("bucket", "key", inputStream);`
    `ResponseModel responseModel = ossStorageConfiguration.upload("bucket", "key", inputStream, objectMetadata);`
    `ResponseModel responseModel = ossStorageConfiguration.upload("bucket", "key", multipartFiles);`
    下载:
    `ResponseModel responseModel = ossStorageConfiguration.download("bucket", "key");`
    获取token:
    `Map<String, String> tokens = ossStorageConfiguration.tokens()`
    获取对象外链:
    `String address = ossStorageConfiguration.chain("bucket", "key")`
* S3
    上传:
    `ResponseModel responseModel = awsS3StorageConfiguration.upload("bucket", "key", inputStream, objectMetadata);`
    `ResponseModel responseModel = awsS3StorageConfiguration.upload("bucket", "key", multipartFiles);`
    下载:
    `ResponseModel responseModel = awsS3StorageConfiguration.download("bucket", "key");`
    获取token:
    `Map<String, String> tokens = awsS3StorageConfiguration.tokens()`
    获取对象外链:
    `String address = awsS3StorageConfiguration.chain("bucket", "key")`
    
#Extend
如果现有实现不满足需求, 可重写execute
OSS:
```java
ossStorageConfiguration.execute(new OSSStorageCallback<ResponseModel>() {
    public ResponseModel execute(OSSClient ossClient) {
        // TODO
        return new ResponseModel();
    }
});
```
S3:
```java
awsS3StorageConfiguration.execute(new OSSStorageCallback<ResponseModel>() {
    public ResponseModel execute(OSSClient ossClient) {
        // TODO
        return new ResponseModel();
    }
});
```

