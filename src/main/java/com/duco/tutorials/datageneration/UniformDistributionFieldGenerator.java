package com.duco.tutorials.datageneration;

public class UniformDistributionFieldGenerator implements FieldGenerator {

	private int sequence = 0;
	private final int numberOfValues;
	private final int base;

	public UniformDistributionFieldGenerator(int base, int numberOfValues) {
		this.numberOfValues = numberOfValues;
		this.base = base;
	}

	@Override
	public String generate(int rowIndex) {
		int val = (sequence++ % numberOfValues) + 1;
		return Integer.toString(val + base);
	}

}
