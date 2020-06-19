package com.redislabs.university.ru102j.dao;

import com.redislabs.university.ru102j.api.GeoQuery;
import com.redislabs.university.ru102j.api.Site;
import java.util.Set;

public interface SiteGeoDao extends SiteDao {
  Set<Site> findByGeo(GeoQuery query);
}
