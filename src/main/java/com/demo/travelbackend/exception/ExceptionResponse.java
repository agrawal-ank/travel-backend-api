package com.demo.travelbackend.exception;

import java.util.Date;

public class ExceptionResponse {
	private Date timestamp = new Date();
	private String message;
	
	public ExceptionResponse(String message) {
		this.message = message;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
