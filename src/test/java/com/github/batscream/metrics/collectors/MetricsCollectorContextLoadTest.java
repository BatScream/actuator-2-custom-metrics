
package com.github.batscream.metrics.collectors;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.batscream.metrics.collectors.JsonMetricsCollector;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = MetricsCollectorContextLoadTest.TestApplication.class)
public class MetricsCollectorContextLoadTest {

    @Autowired
    private JsonMetricsCollector collector;

    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    public void ensureThatTheMetricsCollectorBeanIsAvailableInTheContext() {
        assertNotNull(collector);
        assertNotNull(meterRegistry);
        assertNotNull(collector.collect(meterRegistry));
    }

    @SpringBootApplication
    static class TestApplication {

        @Bean
        public JsonMetricsCollector jsonMetricsCollector() {
            return new JsonMetricsCollector();
        }

        @Bean
        public MeterRegistry meterRegistry() {
            return new CompositeMeterRegistry();
        }

    }

}
