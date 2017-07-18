package com.github.fanfever.fever.web.controller;

import com.github.fanfever.fever.web.controller.response.FileInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by fanfever on 2017/7/17.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Slf4j
@RestController
@RequestMapping(value = "/wopi/files")
public class WOPIHostController {

    @Value("${tempFileCatalog}")
    private String tempFileCatalog;
    private final static String demo_url = "https://s3.cn-north-1.amazonaws.com.cn/staging-crm-storage/attachment/2017-7-7/o_1bkdugmbm76s1nb4rm41vetgtmi.xlsx";

    @GetMapping("/{name}")
    public FileInfoResponse getFileInfo(@PathVariable(name = "name") String name) throws IOException, NoSuchAlgorithmException {
        URL url = new URL(demo_url);
        Resource resource = new UrlResource(url);
        File file = new File(tempFileCatalog + resource.getFilename());
        FileUtils.copyURLToFile(url, file);
        return new FileInfoResponse().convert(file);
    }

    @GetMapping(value = "/{name}/contents")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "name") String name) throws IOException {
        URL url = new URL(demo_url);
        Resource resource = new UrlResource(url);
        File file = new File(tempFileCatalog + resource.getFilename());
        FileUtils.copyURLToFile(url, file);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .body(resource);
    }
}
