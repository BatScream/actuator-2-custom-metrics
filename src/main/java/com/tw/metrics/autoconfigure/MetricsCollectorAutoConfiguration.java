
package com.tw.metrics.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tw.metrics.MetricsCollector;

@Configuration
@AutoConfigureAfter(MetricsEndpointAutoConfiguration.class)
public class MetricsCollectorAutoConfiguration {

    @Bean
    public MetricsCollector metricsCollector() {
        return new MetricsCollector();
    }

}
