package com.duco.tutorials.datageneration;

import static java.util.stream.Collectors.joining;

import java.util.List;

public class BasicLineGenerator implements LineGenerator {

	private final List<FieldGenerator> fieldGenerators;

	public BasicLineGenerator(List<FieldGenerator> fieldGenerators) {
		this.fieldGenerators = fieldGenerators;
	}
	
	public String generateLine(int rowIndex) {
		return fieldGenerators.stream()
				.map(fg -> fg.generate(rowIndex))
				.collect(joining(","));
	}
	
}
