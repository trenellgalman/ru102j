package com.redislabs.university.ru102j.core;

public class KeyHelper {

  private static final String defaultPrefix = "app";

  private static String prefix = null;

  public static String getKey(String key) {
    return getPrefix() + ":" + key;
  }

  public static String getPrefix() {
    if (prefix != null) {
      return prefix;
    } else {
      return defaultPrefix;
    }
  }

  public static void setPrefix(String keyPrefix) {
    prefix = keyPrefix;
  }
}
