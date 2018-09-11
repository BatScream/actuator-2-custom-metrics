package com.tw.metrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author clement
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricsCollectorTest {

	@Autowired
	private MetricsCollector collector;

	@Test
	public void whenCollectIsInvokedItShouldReturnAMapContainingExpectedJVMMemoryMetrics() {
		System.out.println(collector.collect());
	}
	
}
