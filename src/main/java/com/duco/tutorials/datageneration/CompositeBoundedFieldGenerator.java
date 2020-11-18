package com.duco.tutorials.datageneration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CompositeBoundedFieldGenerator implements FieldGenerator {

	private final Iterator<GeneratorAndBound> bounds;
	private GeneratorAndBound bound;
	private int indexInBound;

	public CompositeBoundedFieldGenerator(GeneratorAndBound... bounds) {
		this.bounds = Arrays.asList(bounds).iterator();
		nextBound();
	}

	@Override
	public String generate(int rowIndex) {
		if (indexInBound++ >= bound.count) {
			nextBound();
		}
		return bound.fieldGenerator.generate(rowIndex);
	}

	private void nextBound() {
		bound = bounds.next();
		indexInBound = 0;
	}

	static class GeneratorAndBound {
		final int count;
		final FieldGenerator fieldGenerator;

		public GeneratorAndBound(int count, FieldGenerator fieldGenerator) {
			this.count = count;
			this.fieldGenerator = fieldGenerator;
		}
	}
}
