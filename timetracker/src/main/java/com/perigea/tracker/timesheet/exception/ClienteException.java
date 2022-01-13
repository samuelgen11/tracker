package com.perigea.tracker.timesheet.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteException extends GenericTimesheetException {

	private static final long serialVersionUID = 2411212561597831050L;

	public ClienteException(String exception) {
		super(exception);
	}
}
