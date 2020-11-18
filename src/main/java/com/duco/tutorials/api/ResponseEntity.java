package com.duco.tutorials.api;

import com.sun.net.httpserver.Headers;

public class ResponseEntity<T> {

	private final T body;
	private final Headers headers;
	private final int statusCode;

	public ResponseEntity(T body, Headers headers, int statusCode) {
		this.body = body;
		this.headers = headers;
		this.statusCode = statusCode;
	}

	public T getBody() {
		return body;
	}

	public Headers getHeaders() {
		return headers;
	}

	public int getStatusCode() {
		return statusCode;
	}
}