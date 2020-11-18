package com.duco.tutorials.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.duco.tutorials.models.Match;
import com.duco.tutorials.models.Record;

public class EqualityRecordMatcher implements RecordMatcher {

	private AtomicLong comparisonCount = new AtomicLong(0);
	private int nanoSecondPause;
	
	/**
	 * 
	 * @param nanoSecondPause a pause to apply to each comparison to make performance difference measurable.
	 */
	public EqualityRecordMatcher(int nanoSecondPause) {
		this.nanoSecondPause = nanoSecondPause;
		
	}
	
	@Override
	public Optional<Match> match(Record lhs, Record rhs) {
		comparisonCount.incrementAndGet();
		if(comparisonCount.get() % 1000 == 0) {
//			System.out.println("Comparison count: " + comparisonCount.get());
//			System.out.println(LocalDateTime.now().toString());
		}

		//Busy wait because interrupts are too slow
		long waitStart = System.nanoTime();
		while(System.nanoTime() < waitStart + nanoSecondPause) {
			continue;
		}
		
		if (recordEquals(lhs, rhs)) {
			return Optional.of(new Match(lhs, rhs));
		} else {
			return Optional.empty();
		}
	}

	private boolean recordEquals(Record lhs, Record rhs) {
		for(int i = 0; i< lhs.getFields().size(); i++) {
			if(!lhs.getFields().get(i).equals(rhs.getFields().get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public long getComparisonCount() {
		return comparisonCount.get();
	}

}