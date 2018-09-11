
package com.tw.metrics;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

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
public class MetricsCollectorTest {

    private MetricsCollector collector = null;

    @Before
    public void setup() {
        collector = new MetricsCollector();
    }

    @Test
    public void whenCollectIsInvokedItShouldReturnAMapContainingExpectedJVMMemoryMetrics() {

        assertThat(collector.collect(buildTestMeterWithMaxHeapMemoryMeter()),
                equalTo(buildeExpectedJsonForMaxHeapMemory()));

    }

    private String buildeExpectedJsonForMaxHeapMemory() {

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
