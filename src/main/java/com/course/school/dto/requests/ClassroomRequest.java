package com.course.school.dto.requests;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "Classroom Request")
public class ClassroomRequest {

	@NotNull
	private String name;

	@NotNull
	private String dayOfWeek;

	public static Classroom toDomain(ClassroomRequest request, Long id) {
		return Classroom.builder().id(id).name(request.getName()).dayOfWeek(request.getDayOfWeek()).build();
	}

}
