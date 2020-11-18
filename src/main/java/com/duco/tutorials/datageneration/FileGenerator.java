package com.duco.tutorials.datageneration;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

public class FileGenerator {

	private final LineGenerator lineGenerator;

	public FileGenerator(LineGenerator lineGenerator) {
		this.lineGenerator = lineGenerator;
	}

	public List<String> generate(int rowCount) {
		return IntStream.range(0, rowCount)
				.mapToObj(lineGenerator::generateLine)
				.collect(toList());
	}

}
