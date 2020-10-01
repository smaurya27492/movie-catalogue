package com.personal.moviesapi.responsemodel;

public class APIResponse implements IBaseResponse {

	private Object response;
	private Object error;

	public APIResponse() {
	}

	public APIResponse(Object response, Object error) {
		super();
		this.response = response;
		this.error = error;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

}
