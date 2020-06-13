package com.redislabs.university.RU102J.resources;

import com.redislabs.university.RU102J.dao.CapacityDao;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/capacity")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CapacityResource {

  private static final Integer defaultLimit = 10;
  private final CapacityDao capacityDao;

  public CapacityResource(CapacityDao capacityDao) {
    this.capacityDao = capacityDao;
  }

  @GET
  @Path("/")
  public Response getCapacity(@PathParam("limit") Integer limit) {
    return Response.ok(capacityDao.getReport(defaultLimit))
        .header("Access-Control-Allow-Origin", "*")
        .build();
  }
}
