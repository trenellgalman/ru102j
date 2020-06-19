package com.redislabs.university.ru102j;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class RediSolarConfiguration extends Configuration {

  private String defaultName = "Bob";
  private RedisConfig redisConfig;

  @JsonProperty
  public String getDefaultName() {
    return defaultName;
  }

  @JsonProperty
  public void setDefaultName(String defaultName) {
    this.defaultName = defaultName;
  }

  @JsonProperty("redis")
  public RedisConfig getRedisConfig() {
    return redisConfig;
  }

  @JsonProperty("redis")
  public void setRedisConfig(RedisConfig config) {
    this.redisConfig = config;
  }
}
