package com.course.school.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.school.entities.StudentClassroom;
import com.course.school.exceptions.BadRequestException;
import com.course.school.exceptions.ConflictException;
import com.course.school.exceptions.NotFoundException;
import com.course.school.services.StudentClassroomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/student-classroom")
@Api(value = "Student Classroom")
public class StudentClassroomController {

	@Autowired
	private StudentClassroomService studentClassroomService;

	@ApiOperation(value = "Insert a student classroom", httpMethod = "POST")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<String> insert(
			@ApiParam(name = "studentId", required = true, value = "Student Id") @RequestParam(name = "studentId") Long studentId,
			@ApiParam(name = "classroomId", required = true, value = "Classroom Id") @RequestParam(name = "classroomId") Long classroomId)
			throws NotFoundException, BadRequestException, ConflictException {
		StudentClassroom studentClassroom = studentClassroomService.insert(studentId, classroomId);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(studentClassroom.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Delete a student classroom", httpMethod = "DELETE")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(
			@ApiParam(name = "id", required = true, value = "Student Classroom Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		studentClassroomService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
