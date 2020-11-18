package com.duco.tutorials;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.duco.tutorials.api.Handler;
import com.duco.tutorials.api.MatchingHandler;
import com.sun.net.httpserver.HttpServer;

public class Application {

	public static final int SERVER_PORT = 8000;

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
		Handler matchingHandler = new MatchingHandler();
		server.createContext("/api/matching", matchingHandler::handle);
		server.setExecutor(null); // creates a default executor
		server.start();
	}
}