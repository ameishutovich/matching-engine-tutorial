package com.duco.tutorials.service;

import static com.duco.tutorials.models.Record.Side.LEFT;
import static com.duco.tutorials.models.Record.Side.RIGHT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.duco.tutorials.loaders.RecordLoader;
import com.duco.tutorials.models.MatchingResult;
import com.duco.tutorials.models.Record;

abstract class BaseMatchingServiceTest {

	private static final String BASE_DIR = "com/duco/tutorials/matching/";
	private static final String LHS_POSTFIX = "_lhs";
	private static final String RHS_POSTFIX = "_rhs";

	private static final int ROW_COUNT = 25_000;
	private static final int UNMATCHED_ROW_COUNT = 1250;
	private static final int UNMATCHED_COUNT = UNMATCHED_ROW_COUNT * 2;
	private static final int MATCHED_COUNT = ROW_COUNT - UNMATCHED_ROW_COUNT;

	protected abstract MatchingService getMatchingService();

	@Test
	void basicTest(TestInfo testInfo) {
		List<Record> lhsRecords = new ArrayList<>();
		lhsRecords.add(new Record(1, LEFT, "a", "1"));
		lhsRecords.add(new Record(2, LEFT, "b", "2"));
		lhsRecords.add(new Record(3, LEFT, "c", "3"));
		lhsRecords.add(new Record(4, LEFT, "d", "4"));

		List<Record> rhsRecords = new ArrayList<>();
		rhsRecords.add(new Record(1, RIGHT, "a", "1"));
		rhsRecords.add(new Record(2, RIGHT, "b", "5"));
		rhsRecords.add(new Record(3, RIGHT, "c", "3"));
		rhsRecords.add(new Record(4, RIGHT, "d", "6"));

		MatchingResult match = getMatchingService().match(lhsRecords, rhsRecords);

		assertThat(match.getUnmatched(), hasSize(4));
		assertThat(match.getUnmatched(), Matchers.containsInAnyOrder(lhsRecords.get(1), lhsRecords.get(3),
				rhsRecords.get(1), rhsRecords.get(3)));
		assertThat(match.getMatches(), hasSize(2));

	}

	/**
	 * Matches two files with three fields. The 2nd field is unique! Expected to
	 * have a 95% match rate.
	 * 
	 * @throws IOException
	 */
	@Test
	public void uniqueSingleFieldInPositionTwo(TestInfo testInfo) throws IOException {
		MatchingResult result = match("uniqueFieldInPosition2", testInfo);
		assertThat(result.getUnmatched(), hasSize(UNMATCHED_COUNT));
		assertThat(result.getMatches(), hasSize(MATCHED_COUNT));
	}

	/**
	 * Matches two fields with three fields in each. Each field is populated with a
	 * set list of values e.g. 1-60. Evenly distributed. No fields are unique.
	 * Expected to have a 95% match rate.
	 */
	@Test
	public void threeEvenlyDistributedFields(TestInfo testInfo) throws IOException {
		MatchingResult result = match("21_value_field_59_value_field_101_value_field", testInfo);
		assertThat(result.getUnmatched(), hasSize(UNMATCHED_COUNT));
		assertThat(result.getMatches(), hasSize(MATCHED_COUNT));
	}

	private MatchingResult match(String baseFileName, TestInfo testInfo) throws IOException {
		String leftFile = baseFileName + LHS_POSTFIX;
		String rightFile = baseFileName + RHS_POSTFIX;
		;
		RecordLoader recordLoader = new RecordLoader(1);

		List<Record> lhsRecords = recordLoader.parse(BASE_DIR + leftFile, LEFT);
		List<Record> rhsRecords = recordLoader.parse(BASE_DIR + rightFile, RIGHT);

		MatchingResult result = getMatchingService().match(lhsRecords, rhsRecords);

		System.out.println(testInfo.getTestClass().get().getSimpleName() + "." + testInfo.getDisplayName()
				+ ": matching completed in " + result.getTime().toString());
		return result;
	}

}
