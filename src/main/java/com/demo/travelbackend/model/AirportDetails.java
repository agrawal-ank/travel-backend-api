package com.demo.travelbackend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "_embedded", "page" })
public class AirportDetails {
	@JsonProperty("_embedded")
	private AirportLocations embedded;

	@JsonProperty("_embedded")
	public AirportLocations getEmbedded() {
		return embedded;
	}

	@JsonProperty("_embedded")
	public void setEmbedded(AirportLocations embedded) {
		this.embedded = embedded;
	}
}
