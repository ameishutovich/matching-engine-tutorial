package com.duco.tutorials.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Record {

	public enum Side{
		LEFT,
		RIGHT
	}

	private int id;
	private Side side;
	private List<String> fields;

	public Record() {
	}

	public Record(int id, Side side, List<String> fields) {
		this.id = id;
		this.side = side;
		this.fields = Collections.unmodifiableList(fields);
	}
	
	public Record(int id, Side side, String...fields) {
		this(id, side, Arrays.asList(fields));
	}

	public int getId() {
		return id;
	}

	public List<String> getFields() {
		return fields;
	}

	public Side getSide() {
		return side;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Record record = (Record) o;
		return id == record.id &&
				side == record.side &&
				Objects.equals(fields, record.fields);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, side, fields);
	}
	
	public String toString() {
		return String.format("[id: %s, side: %s, fields: %s]", id, side, fields);
	}
}
