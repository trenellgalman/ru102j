package com.redislabs.university.ru102j.resources;

import com.redislabs.university.ru102j.api.MeterReading;
import com.redislabs.university.ru102j.dao.CapacityDao;
import com.redislabs.university.ru102j.dao.FeedDao;
import com.redislabs.university.ru102j.dao.MetricDao;
import com.redislabs.university.ru102j.dao.SiteStatsDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/meterReadings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeterReadingResource {
  private static final Integer MAX_RECENT_FEEDS = 1000;
  private static final Integer DEFAULT_RECENT_FEEDS = 100;
  private final SiteStatsDao siteStatsDao;
  private final MetricDao metricDao;
  private final CapacityDao capacityDao;
  private final FeedDao feedDao;

  public MeterReadingResource(
      SiteStatsDao siteStatsDao, MetricDao metricDao, CapacityDao capacityDao, FeedDao feedDao) {
    this.siteStatsDao = siteStatsDao;
    this.metricDao = metricDao;
    this.capacityDao = capacityDao;
    this.feedDao = feedDao;
  }

  @POST
  public Response addAll(List<MeterReading> readings) {
    for (MeterReading reading : readings) {
      add(reading);
    }

    return Response.accepted().build();
  }

  public Response add(MeterReading reading) {
    metricDao.insert(reading);
    siteStatsDao.update(reading);
    capacityDao.update(reading);
    feedDao.insert(reading);

    return Response.accepted().build();
  }

  @GET
  public Response getGlobal(@PathParam("n") Integer count) {
    List<MeterReading> readings = feedDao.getRecentGlobal(getFeedCount(count));
    return Response.ok(readings).header("Access-Control-Allow-Origin", "*").build();
  }

  @GET
  @Path("/{id}")
  public Response getForSite(@PathParam("id") Long id, @PathParam("n") Integer count) {
    List<MeterReading> readings = feedDao.getRecentForSite(id, getFeedCount(count));
    return Response.ok(readings).header("Access-Control-Allow-Origin", "*").build();
  }

  private Integer getFeedCount(Integer count) {
    if (count == null || count < 0) {
      return DEFAULT_RECENT_FEEDS;
    } else if (count > MAX_RECENT_FEEDS) {
      return MAX_RECENT_FEEDS;
    } else {
      return count;
    }
  }
}
