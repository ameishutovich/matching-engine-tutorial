package com.duco.tutorials.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.duco.tutorials.models.MatchingResult;
import com.duco.tutorials.service.MatchingService;
import com.duco.tutorials.service.MatchingServiceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class MatchingHandler extends Handler {

	private final MatchingService service;

	public MatchingHandler() {
		this(MatchingServiceFactory.defaultStrategy());
	}

	public MatchingHandler(MatchingService matchingService) {
		super(new ObjectMapper());
		this.service = matchingService;
	}

	@Override
	protected void execute(HttpExchange exchange) throws Exception {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
		exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST,OPTIONS");
		if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
			exchange.sendResponseHeaders(204, -1);
			return;
		}
		ResponseEntity<MatchingResult> e = doPost(exchange.getRequestBody());
		exchange.getResponseHeaders().putAll(e.getHeaders());
		exchange.sendResponseHeaders(e.getStatusCode(), 0);
		OutputStream os = exchange.getResponseBody();
		os.write(super.writeResponse(e.getBody()));
		os.close();
	}

	private ResponseEntity<MatchingResult> doPost(InputStream is) throws IOException {
		MatchingRequest matchingReq = super.readRequest(is, MatchingRequest.class);
		MatchingResult result = service.match(matchingReq.getLeftSide(), matchingReq.getRightSide());
		return new ResponseEntity<>(result,	getHeaders(CONTENT_TYPE, APPLICATION_JSON), OK);
	}
}
