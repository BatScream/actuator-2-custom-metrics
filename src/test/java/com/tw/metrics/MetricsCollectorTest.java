package com.tw.metrics;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricsCollectorTest {

	@Autowired
	private MetricsCollector collector;


	@Test
	public void whenCollectIsInvokedItShouldReturnAMapContainingExpectedJVMMemoryMetrics() {

		assertThat(collector.collect().keySet(), hasItems("jvm.memory.max", "jvm.memory.max.heap"));

	}

}
