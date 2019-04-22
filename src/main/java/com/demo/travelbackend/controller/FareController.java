package com.demo.travelbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.travelbackend.model.FareDetails;
import com.demo.travelbackend.service.FlightService;

@RestController
@RequestMapping("/fare")
public class FareController {
	@Autowired
	FlightService flightService;
	
	@GetMapping("/{origin}/{destination}")
	public FareDetails getFair(@PathVariable("origin") String origin, 
			@PathVariable("destination") String destination,
			@RequestParam(value = "currency", defaultValue="EUR") String currency) {
		return flightService.getFareDetails(origin, destination, currency);
	}
}
