package com.ipl.xpto.trackingTelemetry.exception;

public class EntityAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -743643339875321639L;

	public EntityAlreadyExistsException(String message) {
		super(message);
	}
}
