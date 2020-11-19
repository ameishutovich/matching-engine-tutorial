package com.duco.tutorials.service;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.duco.tutorials.models.Match;
import com.duco.tutorials.models.MatchingResult;
import com.duco.tutorials.models.Record;

/**
 * This class assumes that the first field is unique.
 *
 */
class SortingMatchingService implements MatchingService {

	private final RecordMatcher comparer = new EqualityRecordMatcher(100);

	public MatchingResult match(List<Record> leftSideSrc, List<Record> rightSideSrc) {
		LocalDateTime start = now();
		List<Record> unmatched = new ArrayList<>();
		List<Match> matches = new ArrayList<>();

		List<Record> leftSide = new LinkedList<>(leftSideSrc);
		List<Record> rightSide = new LinkedList<>(rightSideSrc);

		Comparator<Record> comp = Comparator.comparing(r -> r.getFields().get(1));

		leftSide.sort(comp);
		rightSide.sort(comp);

		Iterator<Record> lhsIter = leftSide.iterator();
		Iterator<Record> rhsIter = rightSide.iterator();
		if (!lhsIter.hasNext() || !rhsIter.hasNext()) {
			return new MatchingResult("Finished", between(start, now()), matches, unmatched);
		}
		Record lhs = lhsIter.next();
		Record rhs = rhsIter.next();
		do {
			int comparison = comp.compare(lhs, rhs);
			if (comparison == 0) {
				// Records have the same unique field 1. Let's see if they match.
				Optional<Match> match = comparer.match(lhs, rhs);
				if (match.isPresent()) {
					matches.add(match.get());
					lhsIter.remove();
					rhsIter.remove();
					
				}
				// By assumption the first fields of each record here are unique. If the records didn't
				// match overall then we can chuck both away.
				if (!lhsIter.hasNext() || !rhsIter.hasNext()) {
					break;
				}
				lhs = lhsIter.next();
				rhs = rhsIter.next();
			} else if (comparison < 0 && lhsIter.hasNext()) {
				lhs = lhsIter.next();
			} else if (comparison > 0 && rhsIter.hasNext()) {
				rhs = rhsIter.next();
			} else {
				// one of iterators must be empty so there's no point going on.
				break;
			}
		} while (true);

		unmatched.addAll(leftSide);
		unmatched.addAll(rightSide);

		LocalDateTime end = now();
		Duration dur = between(start, end);
		return new MatchingResult("Finished", dur, matches, unmatched);
	}

}
