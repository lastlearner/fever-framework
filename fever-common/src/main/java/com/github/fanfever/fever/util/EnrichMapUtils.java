package com.github.fanfever.fever.util;

import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展{@link MapUtils}
 * @author scott he
 * @date 2017/5/18
 */
public class EnrichMapUtils extends MapUtils {
  public static Map<String, String> convertArrayToString(Map<String, String[]> stringMap) {
    Map<String, String> returnMap = new HashMap<>();
    if (isNotEmpty(stringMap)) {
      for(Map.Entry<String, String[]> entry : stringMap.entrySet()) {
        returnMap.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue()[0]);
      }
    }
    return returnMap;
  }
}
