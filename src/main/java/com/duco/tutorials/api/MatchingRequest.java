package com.duco.tutorials.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.duco.tutorials.models.Record;

public class MatchingRequest {

	private List<List<String>> leftSide;
	private List<List<String>> rightSide;

	public MatchingRequest() {
	}

	public MatchingRequest(List<List<String>> leftSide,
			List<List<String>> rightSide) {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
	}

	public List<Record> getLeftSide() {
		return IntStream.range(0, leftSide.size())
				.mapToObj(i -> new Record(i, Record.Side.LEFT, leftSide.get(i)))
				.collect(Collectors.toList());
	}

	public List<Record> getRightSide() {
		return IntStream.range(0, rightSide.size())
				.mapToObj(i -> new Record(i, Record.Side.RIGHT, rightSide.get(i)))
				.collect(Collectors.toList());
	}
}
