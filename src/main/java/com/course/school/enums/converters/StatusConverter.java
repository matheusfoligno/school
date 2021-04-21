package com.course.school.enums.converters;

import java.util.Arrays;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.course.school.enums.Status;

@Converter
public class StatusConverter implements AttributeConverter<Status, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Status attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getId();
	}

	@Override
	public Status convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return null;
		}
		return Arrays.asList(Status.values()).stream().filter(status -> status.getId().equals(dbData)).findAny().get();
	}
}
