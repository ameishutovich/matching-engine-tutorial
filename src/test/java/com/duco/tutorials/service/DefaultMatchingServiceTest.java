package com.duco.tutorials.service;

public class DefaultMatchingServiceTest extends BaseMatchingServiceTest {

	@Override
	protected MatchingService getMatchingService() {
		return new DefaultMatchingService();
	};

}
