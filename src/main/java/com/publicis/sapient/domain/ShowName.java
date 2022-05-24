package com.publicis.sapient.domain;

public enum ShowName {
	MORNING("Morning"), AFTERNOON("Afternoon"), EVENING("Evening"), NIGHT("Night");

	private String status;

	private ShowName(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
