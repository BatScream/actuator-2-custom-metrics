
package com.github.batscream.metrics.collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.github.batscream.metrics.collectors.JsonMetricsCollector;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Meter.Type;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Statistic;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

/**
 * @author clement
 *
 */
public class JsonMetricsCollectorTest {

    private JsonMetricsCollector collector = null;

    @Before
    public void setup() {
        collector = new JsonMetricsCollector();
    }

    @Test
    public void whenCollectIsInvokedItShouldReturnAMapContainingExpectedJVMMemoryMetrics() {

        assertThat(collector.collect(buildTestMeterWithMaxHeapMemoryMeter()),
                equalTo(buildExpectedJsonForMaxHeapMemory()));

    }

    private String buildExpectedJsonForMaxHeapMemory() {

        Properties properties = new Properties();
        properties.put("jmx.memory.max.heap", 0.0);
        return new PropertiesToJsonConverter().parseToJson(properties);
    }

    private MeterRegistry buildTestMeterWithMaxHeapMemoryMeter() {
        CompositeMeterRegistry meterRegistry = new CompositeMeterRegistry();
        Meter.builder("jmx.memory.max", Type.GAUGE,
                Arrays.asList(new Measurement(() -> new Double(0), Statistic.VALUE)))
                .tag("area", "heap").register(meterRegistry);
        return meterRegistry;
    }

}
