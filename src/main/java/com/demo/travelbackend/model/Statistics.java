package com.demo.travelbackend.model;

public class Statistics {
	private int totalRequest;
	private int totalOk;
	private int total4xx;
	private int total5xx;
	private long avgResponseTimeOk;
	private long minResponseTimeOk;
	private long maxResponseTimeOk;

	public Statistics() {
	}

	public Statistics(int totalRequest, int totalOk, int total4xx, int total5xx, long avgResponseTimeOk,
			long minResponseTimeOk, long maxResponseTimeOk) {
		this.totalRequest = totalRequest;
		this.totalOk = totalOk;
		this.total4xx = total4xx;
		this.total5xx = total5xx;
		this.avgResponseTimeOk = avgResponseTimeOk;
		this.minResponseTimeOk = minResponseTimeOk;
		this.maxResponseTimeOk = maxResponseTimeOk;
	}

	public int getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}

	public int getTotalOk() {
		return totalOk;
	}

	public void setTotalOk(int totalOk) {
		this.totalOk = totalOk;
	}

	public int getTotal4xx() {
		return total4xx;
	}

	public void setTotal4xx(int total4xx) {
		this.total4xx = total4xx;
	}

	public int getTotal5xx() {
		return total5xx;
	}

	public void setTotal5xx(int total5xx) {
		this.total5xx = total5xx;
	}

	public long getAvgResponseTimeOk() {
		return avgResponseTimeOk;
	}

	public void setAvgResponseTimeOk(long avgResponseTimeOk) {
		this.avgResponseTimeOk = avgResponseTimeOk;
	}

	public long getMinResponseTimeOk() {
		return minResponseTimeOk;
	}

	public void setMinResponseTimeOk(long minResponseTimeOk) {
		this.minResponseTimeOk = minResponseTimeOk;
	}

	public long getMaxResponseTimeOk() {
		return maxResponseTimeOk;
	}

	public void setMaxResponseTimeOk(long maxResponseTimeOk) {
		this.maxResponseTimeOk = maxResponseTimeOk;
	}
}
