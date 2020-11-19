package com.duco.tutorials.service;

public class MatchingServiceFactory {

	public static MatchingService defaultStrategy() {
		return new DefaultMatchingService();
	}
	
	public static MatchingService sortingMatchingService() {
		return new SortingMatchingService();
	}
	
	
	public static MatchingService groupingMatchingService() {
		return new GroupingMatchingService();
	}
}
