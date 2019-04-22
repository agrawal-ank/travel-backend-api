package com.demo.travelbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.travelbackend.model.Statistics;
import com.demo.travelbackend.service.MetricService;

@RestController
@RequestMapping("/metric")
public class MetricController {
	@Autowired
	MetricService metricService;
	
	@GetMapping
	public Statistics getMetrics() {
		return metricService.getResponseCodeMetric();
	}
}
