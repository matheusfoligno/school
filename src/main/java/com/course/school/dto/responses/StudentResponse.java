package com.course.school.dto.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
@ApiModel(value = "Student Response")
public class StudentResponse {

	private Long id;

	private String name;

	private int age;

	private Status status;

	private List<ClassroomResponse> classrooms;

	public static StudentResponse toResponse(Student student, boolean withClassrooms) {
		StudentResponseBuilder builder = StudentResponse.builder().classrooms(new ArrayList<>());

		if (withClassrooms && Objects.nonNull(student.getStudentClassroom())) {
			builder.classrooms(student.getStudentClassroom().stream()
					.map(sc -> ClassroomResponse.toResponse(sc.getClassroom(), false)).collect(Collectors.toList()));
		}

		return builder.id(student.getId()).name(student.getName()).age(student.getAge()).status(student.getStatus())
				.build();
	}

}
