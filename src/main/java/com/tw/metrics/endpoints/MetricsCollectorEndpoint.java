
package com.tw.metrics.endpoints;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import com.tw.metrics.collectors.JsonMetricsCollector;

import io.micrometer.core.instrument.MeterRegistry;

@Endpoint(
        id = "metrics-all")
public class MetricsCollectorEndpoint {

    private final MeterRegistry registry;

    public MetricsCollectorEndpoint(MeterRegistry registry) {
        this.registry = registry;
    }

    @ReadOperation
    public String getMetrics() {
        return new JsonMetricsCollector().collect(registry);
    }

}
