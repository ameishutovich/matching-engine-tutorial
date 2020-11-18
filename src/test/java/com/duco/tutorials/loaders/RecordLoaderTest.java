package com.duco.tutorials.loaders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.duco.tutorials.models.Record;
import com.duco.tutorials.models.Record.Side;

public class RecordLoaderTest {

	@Test
	public void successfulLoad() throws IOException {
		RecordLoader recordLoader = new RecordLoader(3);
		List<Record> records = recordLoader.parse("com/duco/tutorials/test/loaders/RecordLoader-test-successful",
				Side.LEFT);

		assertThat(records, hasSize(3));
		assertRecord(records.get(0), 0, Side.LEFT, "a", "1", "alpha");
		assertRecord(records.get(1), 1, Side.LEFT, "b", "2", "bravo");
		assertRecord(records.get(2), 2, Side.LEFT, "c", "3", "charlie");
	}

	@Test
	public void loadWithBadLineThrowsException() {
		RecordLoader recordLoader = new RecordLoader(3);
		Assertions.assertThrows(RuntimeException.class, () -> {
			recordLoader.parse("com/duco/tutorials/test/loaders/RecordLoader-test-bad-line", Side.LEFT);
		});

	}

	private void assertRecord(Record record, int index, Side side, String... fields) {
		assertThat(record.getId(), is(equalTo(index)));
		assertThat(record.getSide(), is(equalTo(side)));
		assertThat(record.getFields(), Matchers.contains(fields));

	}
}
