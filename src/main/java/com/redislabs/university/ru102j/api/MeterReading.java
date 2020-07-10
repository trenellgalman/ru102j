package com.redislabs.university.ru102j.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* Represents a solar meter reading submitted at a particular
 * time through the API. These readings are used to generate
 * charts, to indicate which solar stations have excess
 * capacity, and to maintain leader boards.
 * The temperature is recorded for correlations against energy usage.
 * The whUsed and whGenerated values represent an amount of energy (in watt-hours)
 * in the minute the reading was created.
 */
public class MeterReading {
  private Long siteId;
  private ZonedDateTime dateTime;
  private Double whUsed;
  private Double whGenerated;
  private Double tempC;

  public MeterReading() {
  }

  public MeterReading(final Map<String, String> map) {
    this.siteId = Long.valueOf(map.get("siteId"));
    this.dateTime = ZonedDateTime.parse(map.get("dateTime"));
    this.whUsed = Double.valueOf(map.get("whUsed"));
    this.whGenerated = Double.valueOf(map.get("whGenerated"));
    this.tempC = Double.valueOf(map.get("tempC"));
  }

  @JsonCreator
  public MeterReading(@JsonProperty("siteId") final Long siteId, @JsonProperty("dateTime") final ZonedDateTime date,
      @JsonProperty("whUsed") final Double whUsed, @JsonProperty("whGenerated") final Double whGenerated,
      @JsonProperty("tempC") final Double tempC) {
    this.siteId = siteId;
    this.dateTime = date;
    this.whUsed = whUsed;
    this.whGenerated = whGenerated;
    this.tempC = tempC;
  }

  @JsonProperty("siteId")
  public Long getSiteId() {
    return siteId;
  }

  @JsonProperty("siteId")
  public void setSiteId(final Long siteId) {
    this.siteId = siteId;
  }

  @JsonProperty("dateTime")
  public ZonedDateTime getDateTime() {
    return dateTime;
  }

  @JsonProperty("dateTime")
  public void setDateTime(final ZonedDateTime dateTime) {
    this.dateTime = dateTime;
  }

  @JsonProperty("whUsed")
  public Double getWhUsed() {
    return whUsed;
  }

  @JsonProperty("whUsed")
  public void setWhUsed(final Double whUsed) {
    this.whUsed = whUsed;
  }

  @JsonProperty("whGenerated")
  public Double getWhGenerated() {
    return whGenerated;
  }

  @JsonProperty("whGenerated")
  public void setWhGenerated(final Double whGenerated) {
    this.whGenerated = whGenerated;
  }

  @JsonProperty("tempC")
  public Double getTempC() {
    return tempC;
  }

  @JsonProperty("tempC")
  public void setTempC(final Double tempC) {
    this.tempC = tempC;
  }

  public Map<String, String> toMap() {
    final Map<String, String> map = new HashMap<>();

    map.put("siteId", String.valueOf(siteId));
    map.put("dateTime", dateTime.toString());
    map.put("whUsed", String.valueOf(whUsed));
    map.put("whGenerated", String.valueOf(whGenerated));
    map.put("tempC", String.valueOf(tempC));

    return map;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final MeterReading that = (MeterReading) o;
    return Objects.equals(siteId, that.siteId) && Objects.equals(dateTime, that.dateTime)
        && Objects.equals(whUsed, that.whUsed) && Objects.equals(whGenerated, that.whGenerated)
        && Objects.equals(tempC, that.tempC);
  }

  @Override
  public int hashCode() {
    return Objects.hash(siteId, dateTime, whUsed, whGenerated, tempC);
  }

  @Override
  public String toString() {
    return "MeterReading{" + "siteId=" + siteId + ", dateTime=" + dateTime + ", whUsed=" + whUsed + ", whGenerated="
        + whGenerated + ", tempC=" + tempC + '}';
  }
}
