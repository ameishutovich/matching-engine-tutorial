package com.duco.tutorials.api;

import static com.duco.tutorials.api.errors.ApplicationException.invalidRequestException;
import static com.duco.tutorials.api.errors.ApplicationException.toApplicationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.duco.tutorials.api.errors.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public abstract class Handler {

	public static final int OK = 200;
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String APPLICATION_JSON = "application/json";
	private final ObjectMapper objectMapper;

	public Handler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void handle(HttpExchange exchange) {
		try {
			execute(exchange);
		} catch (Throwable e) {
			handle(e, exchange);
		}
	}

	protected abstract void execute(HttpExchange exchange) throws Exception;

	protected <T> T readRequest(InputStream is, Class<T> type) throws IOException {
		try {
			return objectMapper.readValue(is, type);
		} catch (Throwable e) {
			throw e;
		}
	}

	protected <T> byte[] writeResponse(T response) {
		try {
			return objectMapper.writeValueAsBytes(response);
		} catch (Throwable e) {
			throw invalidRequestException();
		}
	}

	protected static Headers getHeaders(String key, String value) {
		Headers headers = new Headers();
		headers.set(key, value);
		return headers;
	}

	public void handle(Throwable throwable, HttpExchange exchange) {
		try {
			throwable.printStackTrace();
			exchange.getResponseHeaders().set(CONTENT_TYPE, APPLICATION_JSON);
			ApplicationException response = getErrorResponse(throwable, exchange);
			OutputStream responseBody = exchange.getResponseBody();
			responseBody.write(objectMapper.writeValueAsBytes(response));
			responseBody.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ApplicationException getErrorResponse(Throwable throwable, HttpExchange exchange)
			throws IOException {
		ApplicationException exc = toApplicationException(throwable);
		exchange.sendResponseHeaders(exc.getCode(), 0);
		return exc;
	}
}