package com.github.fanfever.fever.web.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fanfever on 2017/7/17.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 *
 * @see https://msdn.microsoft.com/zh-cn/library/hh622920(v=office.12).aspx
 */

@Data
public class FileInfoResponse{

    @JsonProperty("BaseFileName")
    private String baseFileName;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Size")
    private Long size;
    @JsonProperty("Version")
    private String version;
    @JsonProperty("SHA256")
    public String sha256;
    @JsonProperty("AllowExternalMarketplace")
    public boolean allowExternalMarketplace = true;

    public FileInfoResponse convert(File s) throws NoSuchAlgorithmException, IOException {
        FileInfoResponse t = new FileInfoResponse();
        t.setBaseFileName(s.getName());
        t.setOwnerId("admin");
        t.setSize(s.length());
        t.setVersion(String.valueOf(s.lastModified()));
        t.setSha256(getHash256(s));
        return t;
    }

    private static String getHash256(File file) throws NoSuchAlgorithmException, IOException {
        String value = "";
        // 获取hash值
        byte[] buffer = new byte[1024];
        int numRead;
        MessageDigest complete;
        try (InputStream fis = new FileInputStream(file)) {
            //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            complete = MessageDigest.getInstance("SHA-256");
            do {
                //从文件读到buffer
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    //用读到的字节进行MD5的计算，第二个参数是偏移量
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            fis.close();
        }
        value = new String(Base64.encodeBase64(complete.digest()));
        return value;
    }
}
