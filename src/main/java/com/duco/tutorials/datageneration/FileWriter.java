package com.duco.tutorials.datageneration;

import static com.duco.tutorials.models.Record.Side.LEFT;
import static com.duco.tutorials.models.Record.Side.RIGHT;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

import com.duco.tutorials.models.Record.Side;

public class FileWriter {

	private static final String DIRECTORY = "src/test/resources/com/duco/tutorials/matching";

	private static final int ROW_COUNT = 25_000;
	private static final int UNMATCHED_COUNT = 1250;

	private static final boolean SHUFFLE = true;

	private static final FieldGenerator lhsMatchUnmatchedGenerator = new MatchUnmatchedFieldGenerator("match",
			ROW_COUNT - UNMATCHED_COUNT, "unmatched_lhs");
	private static final FieldGenerator rhsMatchUnmatchedGenerator = new MatchUnmatchedFieldGenerator("match",
			ROW_COUNT - UNMATCHED_COUNT, "unmatched_rhs");

	public static void main(String args[]) throws IOException {
		if (ROW_COUNT % UNMATCHED_COUNT != 0) {
			throw new RuntimeException(
					"We recommend setting UNMATCHED_COUNT as a factor of ROW_COUNT to make numbers easier...");
		}

		List<FieldGenerator> fieldGenerators;

		// Unique field in position 2

		String fileName = "uniqueFieldInPosition2";

		List<FieldGenerator> baseFieldGenerators = asList(new UniformDistributionFieldGenerator(1, 60),
				new UniqueSequentialFieldGenerator(1), new UniformDistributionFieldGenerator(13, 100));

		writeFile(fileName, baseFieldGenerators, LEFT);
		
		baseFieldGenerators = asList(new UniformDistributionFieldGenerator(1, 60),
				new UniqueSequentialFieldGenerator(1), new UniformDistributionFieldGenerator(13, 100));
		writeFile(fileName, baseFieldGenerators, RIGHT);


		// Three evenly distributed fields
		
		fileName = "21_value_field_59_value_field_101_value_field";

		baseFieldGenerators = asList(new UniformDistributionFieldGenerator(1, 21),
				new UniformDistributionFieldGenerator(1, 59), new UniformDistributionFieldGenerator(1, 101));

		writeFile(fileName, baseFieldGenerators, LEFT);
		
		baseFieldGenerators = asList(new UniformDistributionFieldGenerator(1, 21),
				new UniformDistributionFieldGenerator(1, 59), new UniformDistributionFieldGenerator(1, 101));
		writeFile(fileName, baseFieldGenerators, RIGHT);
		
	}

//	private static List<FieldGenerator> createGenerators(Side side) {
//
////		return Collections.singletonList(new UniqueSequentialFieldGenerator(1));
////		int unmatchedOffset = side == LEFT ? ROW_COUNT - UNMATCHED_COUNT + 1 : ROW_COUNT + 1;
////		return Collections.singletonList(new CompositeBoundedFieldGenerator(
////				new GeneratorAndBound(ROW_COUNT - UNMATCHED_COUNT, new UniqueSequentialFieldGenerator(1)),
////				new GeneratorAndBound(UNMATCHED_COUNT, new UniqueSequentialFieldGenerator(unmatchedOffset))));
//
//		List<FieldGenerator> generators = new ArrayList<>();
//
//		generators.add(new UniformDistributionFieldGenerator(21));
//		generators.add(new UniformDistributionFieldGenerator(59));
//		generators.add(new UniformDistributionFieldGenerator(101));
//
//		String unmatchedValue = "unmatched_" + (side == LEFT ? "lhs" : "rhs");
//		FieldGenerator matchUnmatchedGenerator = new MatchUnmatchedFieldGenerator("match", ROW_COUNT - UNMATCHED_COUNT,
//				unmatchedValue);
//		generators.add(matchUnmatchedGenerator);
//
//		return generators;
//
//	}

	private static void writeFile(String fileName, List<FieldGenerator> fieldGenerators, Side side) throws IOException {
		String sideString = side == LEFT ? "lhs" : "rhs";
		File targetFile = new File(DIRECTORY, fileName + "_" + sideString);

		int moduloSkip = ROW_COUNT / UNMATCHED_COUNT;
		int moduloSkipOffset = side == LEFT ? 0 : moduloSkip / 2;

		FileGenerator fileGenerator = new FileGenerator(
				new ModuloSkippingLineGenerator(moduloSkip, moduloSkipOffset, new BasicLineGenerator(fieldGenerators)));
		List<String> lines = fileGenerator.generate(ROW_COUNT);
		if (SHUFFLE) {
			Collections.shuffle(lines);
		}

		Files.write(targetFile.toPath(), lines, Charset.defaultCharset());
	}

}
