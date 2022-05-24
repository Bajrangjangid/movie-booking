package com.publicis.sapient.domain;

public enum ShowStatus {
	RUNNING("Running"), UPCOMING("Upcoming"), EXPIRED("Expired");

	private String status;

	private ShowStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
