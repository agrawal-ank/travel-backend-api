package com.demo.travelbackend.model;

public class AirportLocation {
	String code;
	String name;
	String description;
	Coordinates coordinates;
	
	public AirportLocation() {
	}

	public AirportLocation(String code, String name, String description, Coordinates coordinates) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.coordinates = coordinates;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public String toString() {
		return "Airport [code=" + code + ", name=" + name + ", description=" + description + ", coordinates="
				+ coordinates + "]";
	}
}
