package com.course.school.dto.responses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.course.school.entities.Classroom;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ApiModel(value = "Classroom Response")
public class ClassroomResponse {

	private Long id;

	private String name;

	private String dayOfWeek;

	private List<StudentResponse> students;

	public static ClassroomResponse toResponse(Classroom entity, boolean withStudents) {
		ClassroomResponseBuilder builder = ClassroomResponse.builder().students(new ArrayList<>());

		if (withStudents && Objects.nonNull(entity.getStudentClassroom())) {
			builder.students(entity.getStudentClassroom().stream()
					.map(sc -> StudentResponse.toResponse(sc.getStudent(), false)).collect(Collectors.toList()));
		}

		return builder.id(entity.getId()).name(entity.getName()).dayOfWeek(entity.getDayOfWeek()).build();
	}

}
