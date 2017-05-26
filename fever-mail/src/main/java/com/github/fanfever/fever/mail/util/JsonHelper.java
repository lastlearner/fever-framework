package com.github.fanfever.fever.mail.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.io.Writer;

/**
 * json格式序列化/反序列化
 * @author scott he
 * @date 2017/4/17
 */
public final class JsonHelper {

  public static class deserialize {
    /**
     * 反序列化
     * @param json json字符串
     * @param typeReference 目标类型
     * @param <T>
     * @return
     */
    public static <T> T convert(String json, TypeReference typeReference) {
      try {
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.<T> readValue(json, typeReference);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }
  }

  public static class serialize {
    /**
     * 序列化
     * @param object 待序列化对象
     * @return
     */
    public static String convert(Object object) {
      Writer writer = new StringWriter();
      try {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(jsonGenerator, object);
        writer.flush();
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage(), e);
      }
      return writer.toString();
    }
  }
}
