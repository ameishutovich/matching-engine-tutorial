package com.duco.tutorials.models;

public class Match {

	private final Record left;
	private final Record right;

	public Match(Record left, Record right) {
		this.left = left;
		this.right = right;
	}

	public Record getLeft() {
		return left;
	}

	public Record getRight() {
		return right;
	}
}
