package com.redislabs.university.ru102j.dao;

import com.redislabs.university.ru102j.api.MeterReading;
import com.redislabs.university.ru102j.api.SiteStats;
import java.time.ZonedDateTime;

public interface SiteStatsDao {
  SiteStats findById(long siteId);

  SiteStats findById(long siteId, ZonedDateTime day);

  void update(MeterReading reading);
}
