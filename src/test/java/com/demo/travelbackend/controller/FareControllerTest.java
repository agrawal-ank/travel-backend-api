package com.demo.travelbackend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.demo.travelbackend.model.AirportLocation;
import com.demo.travelbackend.model.Coordinates;
import com.demo.travelbackend.model.FareDetails;
import com.demo.travelbackend.service.FlightService;
import com.demo.travelbackend.statistics.MetricFilter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FareController.class)
public class FareControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private FlightService flightService;
	
	@MockBean
	private MetricFilter metricFilter;

	@Test
	public void testGetFairWithValidCodes() throws Exception {
		FareDetails fareDetails = new FareDetails(166.01, "EUR",
				new AirportLocation("YOW", "Ottawa International", "Ottawa - Ottawa International (YOW), Canada",
						new Coordinates(45.32083, -75.67278)),
				new AirportLocation("BBA", "location.airport.BBA.long",
						"location.city.BBA.long - location.airport.BBA.long (BBA), Chile",
						new Coordinates(-45.916389, -71.686944)));

		when(flightService.getFareDetails(anyString(), anyString(), anyString())).thenReturn(fareDetails);

		RequestBuilder requestBuilder = get("/fare/YOW/BBA").accept(MediaType.APPLICATION_JSON);
		mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
	}

}
