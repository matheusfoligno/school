package com.course.school.dto.requests;

import javax.validation.constraints.NotNull;

import com.course.school.entities.Student;
import com.course.school.enums.Status;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ApiModel(value = "Student Request")
public class StudentRequest {

	@NotNull
	private String name;

	@NotNull
	private int age;

	@NotNull
	private Status status;

	public static Student toDomain(StudentRequest request, Long id) {
		return Student.builder().id(id).name(request.getName()).age(request.getAge()).status(request.getStatus())
				.build();
	}

}
