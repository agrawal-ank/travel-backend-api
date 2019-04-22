package com.demo.travelbackend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import com.demo.travelbackend.controller.AirportController;
import com.demo.travelbackend.exception.NotFoundException;
import com.demo.travelbackend.model.AirportLocation;
import com.demo.travelbackend.model.Coordinates;
import com.demo.travelbackend.service.FlightService;
import com.demo.travelbackend.service.MetricService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AirportController.class)
public class AirportControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private FlightService flightService;

	@MockBean
	private MetricService metricService;

	@Test
	public void testGetAirports() throws Exception {
		List<AirportLocation> expectedAirports = Arrays.asList(
				new AirportLocation("YOW", "Ottawa International", "Ottawa - Ottawa International (YOW), Canada",
						new Coordinates(45.32083, -75.67278)),
				new AirportLocation("BBA", "location.airport.BBA.long",
						"location.city.BBA.long - location.airport.BBA.long (BBA), Chile",
						new Coordinates(-45.916389, -71.686944)));

		when(flightService.getAirports(anyString(), anyString())).thenReturn(expectedAirports);

		RequestBuilder requestBuilder = get("/airports").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		String expetced = new ObjectMapper().writeValueAsString(expectedAirports);
		String actual = result.getResponse().getContentAsString();

		JSONAssert.assertEquals(expetced, actual, true);
	}

	@Test
	public void testGetAirportsWithValidTerm() throws Exception {
		List<AirportLocation> expectedAirports = Arrays.asList(
				new AirportLocation("HNL", "Honolulu Intl.", "Honolulu - Honolulu Intl. (HNL), USA",
						new Coordinates(21.32583, -157.92167)),
				new AirportLocation("SDF", "Louisville Intl.", "Louisville - Louisville Intl. (SDF), USA",
						new Coordinates(38.17972, -85.73861)));

		when(flightService.getAirports(anyString(), anyString())).thenReturn(expectedAirports);

		RequestBuilder requestBuilder = get("/airports?term=USA").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		String expetced = new ObjectMapper().writeValueAsString(expectedAirports);
		String actual = result.getResponse().getContentAsString();

		JSONAssert.assertEquals(expetced, actual, true);
	}
	
	@Test
	public void testGetAirportsWithInvalidTerm() throws Exception {
		when(flightService.getAirports(anyString(), anyString())).thenThrow(new NotFoundException("404 Not Found"));

		RequestBuilder requestBuilder = get("/airports?term=USAA").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().is4xxClientError()).andReturn();
	}
	
	@Test
	public void testGetAirportByCodeWithVaildCode() throws Exception {
		AirportLocation location = new AirportLocation("YOW", "Ottawa International", "Ottawa - Ottawa International (YOW), Canada",
						new Coordinates(45.32083, -75.67278));

		when(flightService.getAirportByCode(anyString(), anyString())).thenReturn(location);

		RequestBuilder requestBuilder = get("/airports/YOW").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

		String expetced = new ObjectMapper().writeValueAsString(location);
		String actual = result.getResponse().getContentAsString();

		JSONAssert.assertEquals(expetced, actual, true);
	}
	
	@Test
	public void testGetAirportByCodeWithInvaildCode() throws Exception {
		when(flightService.getAirportByCode(anyString(), anyString())).thenThrow(new NotFoundException("404 Not Found"));

		RequestBuilder requestBuilder = get("/airports/YOWW").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().is4xxClientError()).andReturn();
	}
}
