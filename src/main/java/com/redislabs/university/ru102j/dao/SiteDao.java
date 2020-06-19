package com.redislabs.university.ru102j.dao;

import com.redislabs.university.ru102j.api.Site;
import java.util.Set;

public interface SiteDao {
  void insert(Site site);

  Site findById(long id);

  Set<Site> findAll();
}
