package com.duco.tutorials.service;

import java.util.Optional;

import com.duco.tutorials.models.Match;
import com.duco.tutorials.models.Record;

public interface RecordMatcher {

	/**
	 * 
	 * @param lhs
	 * @param rhs
	 * @return An empty Optional if the records don't match, a populated Option with a Match of lhs and rhs if they do.
	 */
	public Optional<Match> match(Record lhs, Record rhs);

	public long getComparisonCount();
}
