package com.duco.tutorials.models;

import java.time.Duration;
import java.util.List;

public class MatchingResult {

	private final String message;
	private final List<Match> matches;
	private final List<Record> unmatched;
	private final Duration time;

	public MatchingResult(String message, Duration time, List<Match> matches,
			List<Record> unmatched) {
		this.message = message;
		this.time = time;
		this.matches = matches;
		this.unmatched = unmatched;
	}

	public String getMessage() {
		return message;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public List<Record> getUnmatched() {
		return unmatched;
	}
	
	public Duration getTime() {
		return time;
	}
}
