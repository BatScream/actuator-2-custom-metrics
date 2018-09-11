
package com.tw.metrics;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.micrometer.core.instrument.MeterRegistry;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricsCollectorApplicationTest {

    @Autowired
    private MetricsCollector collector;

    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    public void ensureThatTheMetricsCollectorBeanIsAvailableInTheContext() {
        assertNotNull(collector);
        assertNotNull(meterRegistry);
        assertNotNull(collector.collect(meterRegistry));
    }

}
