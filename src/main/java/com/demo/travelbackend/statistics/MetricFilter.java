package com.demo.travelbackend.statistics;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.travelbackend.service.MetricService;

@Component
public class MetricFilter implements Filter {
	@Autowired
	private MetricService metricService;

	@Autowired
	Executor executor;

	/**
	 * Keeps track on all the request and share response code & time metrics 
	 * to MetricService to keep record on it.
	 * 
	 * Runs in separate thread to avoid any impact on user-experience
	 *   
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.setAttribute("StartTime", System.currentTimeMillis());

		chain.doFilter(request, response);

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		int status = httpResponse.getStatus();
		long responseTime = System.currentTimeMillis() - (Long) httpRequest.getAttribute("StartTime");

		executor.execute(() -> metricService.updateMetric(status, responseTime));
	}
}
