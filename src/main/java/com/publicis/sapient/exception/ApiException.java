package com.publicis.sapient.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	private String code;
	private HttpStatus status;
	private Exception ex;

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiException(String message) {
		this.message = message;
	}

	public ApiException(HttpStatus status, String message,Exception ex) {
//		super(code, ex);
		this.status = status;
		this.message = message;
		this.ex = ex;
	}

	public ApiException(HttpStatus status, String message) {
		super(new Exception(message));
		this.message = message;
		this.status = status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
	}

	@Override
	public String toString() {
		return "ApiException{" +
				"message='" + message + '\'' +
				", status=" + status +
				", ex=" + ex +
				'}';
	}
}
