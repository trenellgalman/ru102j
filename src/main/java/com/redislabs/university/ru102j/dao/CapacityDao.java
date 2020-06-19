package com.redislabs.university.ru102j.dao;

import com.redislabs.university.ru102j.api.CapacityReport;
import com.redislabs.university.ru102j.api.MeterReading;

public interface CapacityDao {
  void update(MeterReading reading);

  CapacityReport getReport(Integer limit);

  Long getRank(Long siteId);
}
