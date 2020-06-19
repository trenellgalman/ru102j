package com.redislabs.university.ru102j.dao;

import com.redislabs.university.ru102j.api.Measurement;
import com.redislabs.university.ru102j.api.MeterReading;
import com.redislabs.university.ru102j.api.MetricUnit;
import java.time.ZonedDateTime;
import java.util.List;

public interface MetricDao {
  void insert(MeterReading reading);

  List<Measurement> getRecent(Long siteId, MetricUnit unit, ZonedDateTime time, Integer limit);
}
