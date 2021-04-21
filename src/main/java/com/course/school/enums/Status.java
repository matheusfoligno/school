package com.course.school.enums;

import lombok.Getter;

@Getter
public enum Status {
	ACTIVE(1, "ACTIVE"), INACTIVE(2, "INACTIVE");

	private Integer id;
	private String description;

	private Status(int id, String description) {
		this.id = id;
		this.description = description;
	}
}
