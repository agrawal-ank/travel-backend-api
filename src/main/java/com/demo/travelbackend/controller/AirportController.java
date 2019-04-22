package com.demo.travelbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.travelbackend.model.AirportLocation;
import com.demo.travelbackend.service.FlightService;

@RestController
@RequestMapping("/airports")
public class AirportController {
	@Autowired
	FlightService flightService;

	@GetMapping
	public List<AirportLocation> getAirports(@RequestParam(value = "lang", defaultValue = "en") String lang,
			@RequestParam(value = "term", defaultValue = "") String term) {
		return flightService.getAirports(lang, term);
	}

	@GetMapping("/{code}")
	public AirportLocation getAirportByCode(@PathVariable("code") String code,
			@RequestParam(value = "lang", defaultValue = "en") String lang) throws InterruptedException, ExecutionException {
		return flightService.getAirportByCode(code, lang);
	}
}
