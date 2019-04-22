package com.demo.travelbackend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import com.demo.travelbackend.model.AirportLocation;
import com.demo.travelbackend.exception.BadRequestException;
import com.demo.travelbackend.exception.NotFoundException;
import com.demo.travelbackend.model.AirportDetails;
import com.demo.travelbackend.model.FareDetails;
import com.demo.travelbackend.model.Fare;

@Service
public class FlightService {
	Logger logger = LoggerFactory.getLogger(FlightService.class);

	@Autowired
	OAuth2RestTemplate restTemplate;

	@Value("${mockservice.hostUrl}")
	private String hostUrl;

	@Value("${mockservice.airportsUri}")
	private String airportsUri;

	@Value("${mockservice.faresUri}")
	private String faresUri;

	@Value("${mockservice.language}")
	private String language;

	@Value("${mockservice.term}")
	private String term;

	@Value("${mockservice.currency}")
	private String currency;

	/**
	 * This method consumes "simple-travel-api-mock" to get airport list based
	 * on search criteria.
	 * 
	 * @param lang
	 * @param searchStr
	 * @return
	 */
	public List<AirportLocation> getAirports(String lang, String searchStr) {
		List<AirportLocation> locations = null;

		final String endpoint = createUrl(airportsUri, "", getQueryParmStr(language, lang, term, searchStr));
		logger.debug("endpoint -> {}" + endpoint);

		try {
			ResponseEntity<AirportDetails> response = restTemplate.getForEntity(endpoint, AirportDetails.class);
			logger.debug("HttpResponse -> {}" + response);
			locations = response.getBody().getEmbedded().getLocations();
		} catch (HttpStatusCodeException se) {
			logger.error("Error in getAirports:\n" + se);
			if (se.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundException(se.getMessage());
			} else if (se.getStatusCode().value() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(se.getMessage());
			} else {
				throw new RuntimeException(se.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return locations;
	}

	/**
	 * This method consumes "simple-travel-api-mock" to get airport details
	 * using airport code.
	 * 
	 * @param code
	 * @param lang
	 * @return
	 */
	public AirportLocation getAirportByCode(final String code, final String lang) {
		AirportLocation airport = null;

		final String endpoint = createUrl(airportsUri, code, getQueryParmStr(language, lang));
		logger.debug("endpoint -> {}" + endpoint);

		try {
			ResponseEntity<AirportLocation> response = restTemplate.getForEntity(endpoint, AirportLocation.class);
			logger.debug("HttpResponse -> {}" + response);
			airport = response.getBody();
		} catch (HttpStatusCodeException se) {
			logger.error("Error in getAirportByCode:\n" + se);
			if (se.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundException(se.getMessage());
			} else if (se.getStatusCode().value() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(se.getMessage());
			} else {
				throw new RuntimeException(se.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return airport;
	}

	/**
	 * This method consumes "simple-travel-api-mock" api to get fare offer based
	 * on origin and destination airport codes.
	 * 
	 * @param origin
	 * @param destination
	 * @param curr
	 * 
	 * @return
	 */
	public Fare getFare(final String origin, final String destination, final String curr) {
		Fare fare = null;

		final String endpoint = createUrl(faresUri, getPathVarStr(origin, destination),
				getQueryParmStr(currency, curr));
		logger.debug("endpoint -> {}" + endpoint);

		try {
			ResponseEntity<Fare> response = restTemplate.getForEntity(endpoint, Fare.class);
			logger.debug("HttpResponse -> {}" + response);
			fare = response.getBody();
		} catch (HttpStatusCodeException se) {
			logger.error("Error in getAirportByCode:\n" + se);
			if (se.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundException(se.getMessage());
			} else if (se.getStatusCode().value() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(se.getMessage());
			} else {
				throw new RuntimeException(se.getMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return fare;
	}

	/**
	 * This method executes requests asynchronously to get details on origin &
	 * destination airports and fair offer and return detailed information.
	 * 
	 * @param origin
	 * @param destination
	 * @param currency
	 * @return
	 */
	public FareDetails getFareDetails(final String origin, final String destination, final String currency) {
		// Fetch Origin Airport Location
		CompletableFuture<AirportLocation> originAirportFtr = CompletableFuture
				.supplyAsync(() -> getAirportByCode(origin, null));

		// Fetch Destination Airport Location
		CompletableFuture<AirportLocation> destinationAirportFtr = CompletableFuture
				.supplyAsync(() -> getAirportByCode(destination, null));

		// Fetch Fair Details
		CompletableFuture<Fare> fareFtr = CompletableFuture.supplyAsync(() -> getFare(origin, destination, currency));

		// Waiting for all the Future to complete
		CompletableFuture.allOf(originAirportFtr, destinationAirportFtr, fareFtr);

		// Preparing Fare object along with origin and destination airport
		// details
		FareDetails fareDetails = new FareDetails();
		try {
			fareDetails.setOrigin(originAirportFtr.get());
			fareDetails.setDestination(destinationAirportFtr.get());
			Fare fare = fareFtr.get();
			fareDetails.setAmount(fare.getAmount());
			fareDetails.setCurrency(fare.getCurrency());
		} catch (InterruptedException e) {
			logger.error("Error in getFareDetails:\n" + e);
			if (e.getCause() instanceof NotFoundException)
				throw new NotFoundException(e.getCause().getMessage());
			throw new RuntimeException("Error during fare computing!");
		} catch (ExecutionException e) {
			logger.error("Error in getFareDetails:\n" + e);
			if (e.getCause() instanceof NotFoundException)
				throw new NotFoundException(e.getCause().getMessage());
			throw new RuntimeException("Error during fare computing!");
		}

		return fareDetails;
	}

	/**
	 * Prepare and return endpoint based on uri, path variables and qyery
	 * parameters details
	 * 
	 * @param uri
	 * @param pathVarStr
	 * @param queryParmStr
	 * 
	 * @return
	 */
	private String createUrl(String uri, String pathVarStr, String queryParmStr) {
		String url = String.join("", hostUrl, uri);

		if (pathVarStr != null && !pathVarStr.trim().equals(""))
			url = String.join("/", url, pathVarStr);

		if (queryParmStr != null && !queryParmStr.trim().equals(""))
			url = String.join("?", url, queryParmStr);

		return url;
	}

	/**
	 * Generic method which takes Query Parameters and its values as input, and
	 * prepare string required to create final rest endpoint.
	 * 
	 * @param queryParams
	 * @return
	 */
	private String getQueryParmStr(String... queryParams) {
		StringBuffer queryParamStr = new StringBuffer();

		for (int i = 0; i < queryParams.length; i = i + 2) {
			String key = queryParams[i];
			String value = queryParams[i + 1];

			if (value == null || value.trim().equals(""))
				continue;

			if (queryParamStr.length() > 1)
				queryParamStr.append("&");

			queryParamStr.append(String.join("=", key, value));
		}

		return queryParamStr.toString();
	}

	/**
	 * Generic method which takes Path Variables as input and prepare string
	 * required to create final rest endpoint.
	 * 
	 * @param pathVars
	 * @return
	 */
	private String getPathVarStr(String... pathVars) {
		return String.join("/", pathVars);
	}
}
