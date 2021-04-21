package com.course.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends javassist.NotFoundException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(final String error) {
		super(error);
	}

}
