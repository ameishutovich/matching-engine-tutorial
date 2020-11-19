package com.duco.tutorials.service;

public class SortingMatchingServiceTest extends BaseMatchingServiceTest {

	@Override
	protected MatchingService getMatchingService() {
		return new SortingMatchingService();
	};

}
