package com.duco.tutorials.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.duco.tutorials.models.Match;
import com.duco.tutorials.models.MatchingResult;
import com.duco.tutorials.models.Record;

/**
 * This MatchingService assumes there is a useful 'grouping key' in one of the columns.
 * It groups data by value in that field and then runs a brute force search over each
 * group.
 *
 */
class GroupingMatchingService implements MatchingService {

	private final RecordMatcher comparer = new EqualityRecordMatcher(100);
	
	public MatchingResult match(List<Record> leftSideSrc, List<Record> rightSideSrc) {
		LocalDateTime start = LocalDateTime.now();
		List<Record> unmatched = new ArrayList<>();
		List<Match> matches = new ArrayList<>();

		Map<String, List<Record>> lhsGroups = groupByField(0, leftSideSrc);
		Map<String, List<Record>> rhsGroups = groupByField(0, rightSideSrc);
		
		Set<String> groupKeys = new HashSet<>();
		groupKeys.addAll(lhsGroups.keySet());
		groupKeys.addAll(rhsGroups.keySet());
		
		for (String key : groupKeys) {
			List<Record> leftSide = lhsGroups.get(key);
			List<Record> rightSide = rhsGroups.get(key);
			
			if(leftSide == null) {
				unmatched.addAll(rightSide);
				continue;
			}
			if(rightSide == null) {
				unmatched.addAll(leftSide);
				continue;
			}
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
		}

		LocalDateTime end = LocalDateTime.now();
		Duration dur = Duration.between(start, end);
		return new MatchingResult("Finished", dur, matches, unmatched);
	}

	private Map<String, List<Record>> groupByField(int groupingField, List<Record> records) {
		Map<String, List<Record>> groups = new HashMap<>();
		
		for (Record record : records) {
			String groupKey = record.getFields().get(groupingField);
			if(!groups.containsKey(groupKey)) {
				groups.put(groupKey, new LinkedList<>());
			}
			groups.get(groupKey).add(record);
		}
		return groups;
	}

}
