package com.hywang.timeline.persistence.exception;

public class DataAccessRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5782140240925568781L;

	/**
	 * 
	 */

	public DataAccessRuntimeException() {
		super();
	}

	public DataAccessRuntimeException(String message) {
		super(message);
	}

	public DataAccessRuntimeException(Throwable cause) {
		super(cause);
	}

}

