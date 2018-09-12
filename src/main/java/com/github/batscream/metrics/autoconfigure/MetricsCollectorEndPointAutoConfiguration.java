
package com.github.batscream.metrics.autoconfigure;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.batscream.metrics.endpoints.MetricsCollectorEndpoint;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * @author clement
 *
 */
@Configuration
@AutoConfigureAfter({
    MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class
})
public class MetricsCollectorEndPointAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnEnabledEndpoint
    public MetricsCollectorEndpoint metricsCollectorEndpoint(MeterRegistry registry) {
        return new MetricsCollectorEndpoint(registry);
    }

}
