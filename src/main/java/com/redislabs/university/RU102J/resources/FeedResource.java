package com.redislabs.university.RU102J.resources;

import com.redislabs.university.RU102J.api.MeterReading;
import com.redislabs.university.RU102J.dao.FeedDao;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/capacity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedResource {

  private static final Integer defaultLimit = 20;
  private final FeedDao feedDao;

  public FeedResource(FeedDao feedDao) {
    this.feedDao = feedDao;
  }

  @GET
  public Response getAllEntries(@QueryParam("limit") Integer limit) {
    if (limit == null) {
      limit = defaultLimit;
    }
    List<MeterReading> readings = feedDao.getRecentGlobal(limit);
    return Response.ok(readings).build();
  }

  @GET
  @Path("/{id}")
  public Response getSingleFeed(@PathParam("id") Long siteId, @QueryParam("limit") Integer limit) {
    if (limit == null) {
      limit = defaultLimit;
    }
    List<MeterReading> readings = feedDao.getRecentForSite(siteId, limit);
    return Response.ok(readings).build();
  }
}
