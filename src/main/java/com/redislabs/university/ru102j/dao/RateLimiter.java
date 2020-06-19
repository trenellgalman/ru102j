package com.redislabs.university.ru102j.dao;

public interface RateLimiter {
  void hit(String name) throws RateLimitExceededException;
}
