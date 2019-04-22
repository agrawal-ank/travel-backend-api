package com.demo.travelbackend.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import com.demo.travelbackend.model.Statistics;

@Service
public class MetricService {
	static AtomicInteger totalResponseCounter;
	static AtomicInteger totalOKResponseCounter;
	static AtomicInteger total4XXCounter;
	static AtomicInteger total5XXCounter;
	static AtomicLong totalResponseTimeOk;
	static AtomicLong minResponseTimeOk;
	static AtomicLong maxResponseTimeOk;

	public MetricService() {
		totalResponseCounter = new AtomicInteger();
		totalOKResponseCounter = new AtomicInteger();
		total4XXCounter = new AtomicInteger();
		total5XXCounter = new AtomicInteger();
		totalResponseTimeOk = new AtomicLong();
		minResponseTimeOk = new AtomicLong(10000);
		maxResponseTimeOk = new AtomicLong();
	}

	/**
	 * Keeps record on 
	 * 	Total Number of Request Processed, 
	 *  Total Number OK Response,   
	 *  Total Number 4xx Response, 
	 *  Total Number 5xx Response
	 * 
	 * @param status
	 * @param responseTime
	 */
	public void updateMetric(final int status, final long responseTime) {
		totalResponseCounter.incrementAndGet();
		if (status >= 200 && status < 300) {
			totalOKResponseCounter.incrementAndGet();
			updateResponseTimeMetricForOk(responseTime);
		} else if (status >= 400 && status < 500) {
			total4XXCounter.incrementAndGet();
		} else if (status >= 500 && status < 600) {
			total5XXCounter.incrementAndGet();
		}
	}

	/**
	 * Computes minimum, maximum and avg response time for 
	 * successful request with status code 200(Ok)
	 * 
	 * @param responseTime
	 */
	private void updateResponseTimeMetricForOk(long responseTime) {
		totalResponseTimeOk.addAndGet(responseTime);
		if (responseTime < minResponseTimeOk.get())
			minResponseTimeOk.set(responseTime);
		if (responseTime > maxResponseTimeOk.get())
			maxResponseTimeOk.set(responseTime);
	}

	/**
	 * Maps Response Code and Response Time Metrics to Object. 
	 * 
	 */
	public Statistics getResponseCodeMetric() {
		long avgResponseTime = (totalOKResponseCounter.get() == 0) ? 0
				: totalResponseTimeOk.get() / totalOKResponseCounter.get();

		long minResponseTime = (totalOKResponseCounter.get() == 0) ? 0 : minResponseTimeOk.get();

		Statistics metrics = new Statistics(totalResponseCounter.get(), totalOKResponseCounter.get(),
				total4XXCounter.get(), total5XXCounter.get(), avgResponseTime, minResponseTime,
				maxResponseTimeOk.get());

		return metrics;
	}
}
