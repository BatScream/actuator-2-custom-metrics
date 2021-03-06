package com.github.batscream.metrics.collectors;

import com.google.common.collect.Maps;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Statistic;
import lombok.NonNull;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** @author clement */
public class JsonMetricsCollector {

  private static final String DELIMITER = ".";
  private static final String LEAF_VALUE_DELIMITER = "_";
  private static final String SPACE_REGEX = "\\s";

  public String collect(@NonNull MeterRegistry registry) {
    List<Meter> meters = registry.getMeters();
    return this.convertToJsonString(
        meters
            .stream()
            .map(meter -> getEntryForMeter(meter))
            .collect(Collectors.toMap(SortedMap.Entry::getKey, SortedMap.Entry::getValue)));
  }

  private Entry<String, Double> getEntryForMeter(@NonNull Meter meter) {
    String key = getKey(meter.getId());
    Double value = getMeasurement(meter);
    return Maps.immutableEntry(key, value);
  }

  private String getKey(@NonNull Id meterId) {
    return new StringBuilder(meterId.getName())
        .append(DELIMITER)
        .append(
            meterId
                .getTags()
                .stream()
                .map(tag -> tag.getValue().replaceAll(SPACE_REGEX, LEAF_VALUE_DELIMITER))
                .collect(Collectors.joining(DELIMITER)))
        .toString();
  }

  private Double getMeasurement(@NonNull Meter meter) {
    return StreamSupport.stream(meter.measure().spliterator(), false)
        .filter(measurement -> measurement.getStatistic() == Statistic.VALUE)
        .findFirst()
        .map(measurement -> measurement.getValue())
        .orElse(0D);
  }

  private String convertToJsonString(@NonNull Map<String, Double> metricsMap) {
    Properties properties = new Properties();
    properties.putAll(metricsMap);
    return new PropertiesToJsonConverter().parseToJson(properties);
  }
}
