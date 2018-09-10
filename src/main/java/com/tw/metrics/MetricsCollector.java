
package com.tw.metrics;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Statistic;
import lombok.NonNull;

/**
 * @author clement
 *
 */
public class MetricsCollector {

    private static final String DELIMITER = ".";
    private static final String LEAF_SPACE_FORMATTER = "_";
    private static final String SPACE_REGEX = "\\s";

    @Autowired
    private MeterRegistry registry;

    public Map<String, Object> collect() {
        List<Meter> meters = registry.getMeters();
        return meters.stream().map(meter -> {
            return getEntryForMeter(meter);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Entry<String, Object> getEntryForMeter(@NonNull Meter meter) {
        String key = getKey(meter.getId());
        Object value = getMeasurement(meter);
        return Maps.immutableEntry(key, value);
    }

    private String getKey(@NonNull Id meterId) {
        return new StringBuilder(meterId.getName()).append(DELIMITER)
                .append(meterId.getTags().stream()
                        .map(tag -> tag.getKey() + DELIMITER
                                + tag.getValue().replaceAll(SPACE_REGEX, LEAF_SPACE_FORMATTER))
                        .collect(Collectors.joining(DELIMITER)))
                .toString();
    }

    private Object getMeasurement(@NonNull Meter meter) {
        return StreamSupport.stream(meter.measure().spliterator(), false)
                .filter(measurement -> measurement.getStatistic() == Statistic.VALUE).findFirst()
                .map((measurement) -> measurement.getValue()).orElse(0D);
    }
}
