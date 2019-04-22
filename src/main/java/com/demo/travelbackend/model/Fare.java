package com.demo.travelbackend.model;

public class Fare {
	double amount;
	String currency;
	String origin;
	String destination;

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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "MockFare [amount=" + amount + ", currency=" + currency + ", origin=" + origin + ", destination="
				+ destination + "]";
	}
}
