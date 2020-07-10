package com.redislabs.university.ru102j.dao;

import com.redislabs.university.ru102j.api.Coordinate;
import com.redislabs.university.ru102j.api.GeoQuery;
import com.redislabs.university.ru102j.api.Site;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class SiteGeoDaoRedisImpl implements SiteGeoDao {
  private static final Double CAPACITY_THRESHOLD = 0.2;
  private final JedisPool jedisPool;

  public SiteGeoDaoRedisImpl(JedisPool jedisPool) {
    this.jedisPool = jedisPool;
  }

  @Override
  public Site findById(long id) {
    try (Jedis jedis = jedisPool.getResource()) {
      Map<String, String> fields = jedis.hgetAll(RedisSchema.getSiteHashKey(id));
      if (fields == null || fields.isEmpty()) {
        return null;
      }
      return new Site(fields);
    }
  }

  @Override
  public Set<Site> findAll() {
    try (Jedis jedis = jedisPool.getResource()) {
      Set<String> keys = jedis.zrange(RedisSchema.getSiteGeoKey(), 0, -1);
      Pipeline pipeline = jedis.pipelined();

      Set<Response<Map<String, String>>> sites = keys.stream().map(pipeline::hgetAll)
          .collect(Collectors.toSet());

      pipeline.sync();

      return sites.stream().map(Response::get).map(Site::new).collect(Collectors.toSet());
    }
  }

  @Override
  public Set<Site> findByGeo(GeoQuery query) {
    if (query.onlyExcessCapacity()) {
      return findSitesByGeoWithCapacity(query);
    } else {
      return findSitesByGeo(query);
    }
  }

  // Challenge #5
  // private Set<Site> findSitesByGeoWithCapacity(GeoQuery query) {
  // return Collections.emptySet();
  // }
  // Comment out the above, and uncomment what's below
  private Set<Site> findSitesByGeoWithCapacity(GeoQuery query) {
    Double radius = query.getRadius();
    double latitude = query.getCoordinate().getLat();
    double longitude = query.getCoordinate().getLng();
    GeoUnit unit = query.getRadiusUnit();

    try (Jedis jedis = jedisPool.getResource()) {
      Pipeline pipeline = jedis.pipelined();
      // START Challenge #5
      // TODO: Challenge #5: Get the sites matching the geo query, store them
      // in List<GeoRadiusResponse> radiusResponses;
      // END Challenge #5

      List<GeoRadiusResponse> radiusResponses = jedis.georadius(RedisSchema.getSiteGeoKey(), longitude, latitude,
          radius, unit);
      Set<Response<Map<String, String>>> siteResponses = radiusResponses.stream()
          .map(response -> pipeline.hgetAll(response.getMemberByString())).collect(Collectors.toSet());

      pipeline.sync();

      Set<Site> sites = siteResponses.stream().map(Response::get).map(Site::new).collect(Collectors.toSet());
      // START Challenge #5

      // TODO: Challenge #5: Add the code that populates the scores HashMap...
      // END Challenge #5
      Map<Long, Response<Double>> scores = sites.stream()
          .map(site -> Map.entry(site.getId(), pipeline.zscore(RedisSchema.getCapacityRankingKey(), site.getId().toString())))
          .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

      pipeline.sync();

      return sites.stream().filter(site -> scores.get(site.getId()).get() >= CAPACITY_THRESHOLD)
          .collect(Collectors.toSet());
    }
  }

  private Set<Site> findSitesByGeo(GeoQuery query) {
    Coordinate coord = query.getCoordinate();
    Double radius = query.getRadius();
    GeoUnit radiusUnit = query.getRadiusUnit();

    try (Jedis jedis = jedisPool.getResource()) {
      List<GeoRadiusResponse> radiusResponses = jedis.georadius(RedisSchema.getSiteGeoKey(), coord.getLng(),
          coord.getLat(), radius, radiusUnit);

      return radiusResponses.stream().map(response -> jedis.hgetAll(response.getMemberByString()))
          .filter(Objects::nonNull).map(Site::new).collect(Collectors.toSet());
    }
  }

  @Override
  public void insert(Site site) {
    try (Jedis jedis = jedisPool.getResource()) {
      String key = RedisSchema.getSiteHashKey(site.getId());
      jedis.hmset(key, site.toMap());

      if (site.getCoordinate() == null) {
        throw new IllegalArgumentException("Coordinate required for Geo " + "insert.");
      }
      double longitude = site.getCoordinate().getGeoCoordinate().getLongitude();
      double latitude = site.getCoordinate().getGeoCoordinate().getLatitude();
      jedis.geoadd(RedisSchema.getSiteGeoKey(), longitude, latitude, key);
    }
  }
}
