package com.duco.tutorials.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.duco.tutorials.models.Match;
import com.duco.tutorials.models.MatchingResult;
import com.duco.tutorials.models.Record;

class DefaultMatchingService implements MatchingService {

	private final RecordMatcher comparer = new EqualityRecordMatcher(100);
	
	public MatchingResult match(List<Record> leftSideSrc, List<Record> rightSideSrc) {
		LocalDateTime start = LocalDateTime.now();
		List<Record> unmatched = new ArrayList<>();
		List<Match> matches = new ArrayList<>();

		List<Record> leftSide = new LinkedList<>(leftSideSrc);
		List<Record> rightSide = new LinkedList<>(rightSideSrc);
		Iterator<Record> leftSideIt = leftSide.iterator();
		while (leftSideIt.hasNext()) {
			Record recL = leftSideIt.next();
			Iterator<Record> rightSideIt = rightSide.iterator();
			while (rightSideIt.hasNext()){
				Record recR = rightSideIt.next();
				Optional<Match> match = comparer.match(recL, recR);
				if (match.isPresent()) {
					matches.add(match.get());
					leftSideIt.remove();
					rightSideIt.remove();
					break;
				}
			}
		}
		unmatched.addAll(leftSide);
		unmatched.addAll(rightSide);

		LocalDateTime end = LocalDateTime.now();
		Duration dur = Duration.between(start, end);
		return new MatchingResult("Finished", dur, matches, unmatched);
	}

}
