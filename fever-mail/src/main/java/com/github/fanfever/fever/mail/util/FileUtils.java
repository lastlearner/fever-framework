package com.github.fanfever.fever.mail.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author scott he
 * @date 2017/5/1
 */
public class FileUtils {
  private final static int BUFFER_SIZE = 102400;

  public static void write(InputStream inputStream, String fileName) throws IOException {
    OutputStream outputStream = null;
    try {
      outputStream = new FileOutputStream(fileName, false);
      copy(inputStream, outputStream);
    } finally {
      if(outputStream != null) {
        outputStream.close();
      }
    }
  }

  public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
    byte[] buffer = new byte[BUFFER_SIZE];
    int c;
    while ((c = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
      outputStream.write(buffer, 0, c);
    }
  }

  public static void copy(File src, File dest) throws IOException {
    org.apache.commons.io.FileUtils.copyFile(src, dest);
  }

}
