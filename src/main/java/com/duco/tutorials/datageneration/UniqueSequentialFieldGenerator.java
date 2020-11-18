package com.duco.tutorials.datageneration;

public class UniqueSequentialFieldGenerator implements FieldGenerator {

	int sequence;

	public UniqueSequentialFieldGenerator(int start) {
		sequence = start;
	}

	@Override
	public String generate(int rowIndex) {
		return Integer.toString(sequence++);
	}

}
