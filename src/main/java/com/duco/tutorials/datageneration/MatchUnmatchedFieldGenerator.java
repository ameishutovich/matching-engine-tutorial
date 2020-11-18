package com.duco.tutorials.datageneration;

/**
 * Field generator to produce an easy way to discriminate matches and unmatched.
 * Produces a specified number of one value and uses another for the rest.
 * In normal usage make the first value the same for both sides and the other
 * different.
 */
public class MatchUnmatchedFieldGenerator implements FieldGenerator {

	private final String matchString;
	private final int unmatchedFrequency;
	private final String unmatchedString;

	public MatchUnmatchedFieldGenerator(String matchString, int unmatchedFrequency,
			String unmatchedString) {
		this.matchString = matchString;
		this.unmatchedFrequency = unmatchedFrequency;
		this.unmatchedString = unmatchedString;
	}

	@Override
	public String generate(int rowIndex) {
		if (rowIndex % unmatchedFrequency == 0 ) {
			return unmatchedString;
		}
		return matchString;
	}

}
