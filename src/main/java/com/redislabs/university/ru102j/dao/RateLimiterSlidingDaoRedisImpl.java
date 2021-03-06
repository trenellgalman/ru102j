package com.redislabs.university.ru102j.dao;

import redis.clients.jedis.JedisPool;

public class RateLimiterSlidingDaoRedisImpl implements RateLimiter {

  private final JedisPool jedisPool;
  private final long windowSizeMS;
  private final long maxHits;

  public RateLimiterSlidingDaoRedisImpl(JedisPool pool, long windowSizeMS, long maxHits) {
    this.jedisPool = pool;
    this.windowSizeMS = windowSizeMS;
    this.maxHits = maxHits;
  }

  // Challenge #7
  @Override
  public void hit(String name) throws RateLimitExceededException {
    // START CHALLENGE #7
    // END CHALLENGE #7
  }
}
