package com.duco.tutorials.service;

public class MatchingServiceFactory {

	public static MatchingService defaultStrategy() {
		return new DefaultMatchingService();
	}

}
