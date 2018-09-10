package com.tw.metrics;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.AvailableTag;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;

import lombok.NonNull;

public class MetricsCollector {

	private Map<String, Object> collector = new LinkedHashMap<String, Object>();

	@Autowired
	private MetricsEndpoint endpoint;

	public Map<String, Object> collect() {
		Set<String> meterNames = endpoint.listNames().getNames();
		Iterator<String> meterIterator = meterNames.iterator();

		while (meterIterator.hasNext()) {
			String meter = meterIterator.next();
			query(meter, null);
		}
		return collector;
	}

	private void query(@NonNull String meter, List<String> tags) {

		MetricResponse response = this.endpoint.metric(meter, tags);

		// capture metrics for response
		this.collector.put(meter, getMeasurement(response));
		// get the list of available tags to drill down

		List<AvailableTag> availableTags = response.getAvailableTags();

		for (int i = 0; i < availableTags.size(); i++) {

			AvailableTag tag = availableTags.get(i);

			String tagId = tag.getTag();

			for (Iterator<String> iterator = tag.getValues().iterator(); iterator.hasNext();) {
				

			}
		}

	}

	private Object getMeasurement(MetricResponse response) {
		return response.getMeasurements().get(0).getValue();
	}
}
