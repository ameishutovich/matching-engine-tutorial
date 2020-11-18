package com.duco.tutorials.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.duco.tutorials.models.Record;
import com.duco.tutorials.models.Record.Side;

/**
 * Loads {@link Record}s from files in CSV format. Uses line number to populate
 * Record.id. Validates the number of fields in each row.
 *
 */
public class RecordLoader {

	private int fieldCount;

	/**
	 * @param path classpath relative path to the file to load
	 */
	public RecordLoader(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	/**
	 * @param path classpath relative path to the file to load
	 * @param side the matching side we are loading this record for
	 * @return a list of parsed records
	 * @throws IOException
	 * 
	 */
	public List<Record> parse(String path, Side side) throws IOException {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);) {
			Stream<String> lines = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines();

			AtomicInteger index = new AtomicInteger();

//			return lines.map(l -> l.split(",")).filter(s -> {
//				if (s.length != fieldCount) {
//					throw new RuntimeException(
//							String.format("Incorrect number of fields in record %d. Expected %d got %d", index.get(),
//									fieldCount, s.length));
//				}
//				return true;
//			}).map(s -> new Record(index.getAndIncrement(), side, Arrays.asList(s))).collect(Collectors.toList());

			return lines.map(l -> l.split(",")).map(s -> new Record(index.getAndIncrement(), side, Arrays.asList(s)))
					.collect(Collectors.toList());

		}
	}

}
