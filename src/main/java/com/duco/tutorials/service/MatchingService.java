package com.duco.tutorials.service;

import java.util.List;

import com.duco.tutorials.models.MatchingResult;
import com.duco.tutorials.models.Record;

public interface MatchingService {
	MatchingResult match(List<Record> leftSide, List<Record> rightSide);
}
