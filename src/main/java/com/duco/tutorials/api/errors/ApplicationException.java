package com.duco.tutorials.api.errors;

public class ApplicationException extends RuntimeException {

	private final int code;

	ApplicationException(int code, String message) {
		super(message);
		this.code = code;
	}

	public static ApplicationException toApplicationException(Throwable e) {
		if (e instanceof ApplicationException){
			return (ApplicationException) e;
		}
		return new ApplicationException(500, "internal error");
	}

	public static RuntimeException invalidRequestException() {
		return new ApplicationException(400, "invalid request");
	}

	public static RuntimeException methodNotAllowed(String message) {
		return new ApplicationException(405, message);
	}

	public static RuntimeException notFound(String message) {
		return new ApplicationException(404, message);
	}

	public int getCode() {
		return code;
	}
}