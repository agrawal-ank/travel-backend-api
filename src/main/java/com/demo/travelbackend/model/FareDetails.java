package com.demo.travelbackend.model;

public class FareDetails {
	double amount;
	String currency;
	AirportLocation origin;
	AirportLocation destination;

	public FareDetails() {
	}

	public FareDetails(double amount, String currency, AirportLocation origin, AirportLocation destination) {
		this.amount = amount;
		this.currency = currency;
		this.origin = origin;
		this.destination = destination;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public AirportLocation getOrigin() {
		return origin;
	}

	public void setOrigin(AirportLocation origin) {
		this.origin = origin;
	}

	public AirportLocation getDestination() {
		return destination;
	}

	public void setDestination(AirportLocation destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Fare [amount=" + amount + ", currency=" + currency + ", origin=" + origin + ", destination="
				+ destination + "]";
	}
}
