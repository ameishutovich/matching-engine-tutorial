package com.duco.tutorials.service;

public class GroupingMatchingServiceTest extends BaseMatchingServiceTest {

	@Override
	protected MatchingService getMatchingService() {
		return new GroupingMatchingService();
	};

}
