package com.course.school.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.school.dto.requests.ClassroomRequest;
import com.course.school.dto.responses.ClassroomResponse;
import com.course.school.entities.Classroom;
import com.course.school.exceptions.NotFoundException;
import com.course.school.services.ClassroomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/classrooms")
@Api(value = "Classrooms")
public class ClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@ApiOperation(value = "Insert a classroom", httpMethod = "POST")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<String> insert(@RequestBody @Valid ClassroomRequest request) {
		Classroom classroom = classroomService.insert(request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(classroom.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Update a classroom", httpMethod = "PUT")
	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> update(@RequestBody @Valid ClassroomRequest request,
			@ApiParam(name = "id", required = true, value = "Class Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		classroomService.update(request, id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Delete a classroom", httpMethod = "DELETE")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(
			@ApiParam(name = "id", required = true, value = "Class Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		classroomService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Get all classroom", httpMethod = "GET")
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Page<ClassroomResponse>> getAll(
			@ApiParam(name = "withStudents", required = false, value = "With Students") @RequestParam(name = "withStudents", required = false) boolean withStudents,
			Pageable pageable) {
		return ResponseEntity.ok(classroomService.getAll(pageable, withStudents));
	}

	@ApiOperation(value = "Get by id", httpMethod = "GET")
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ClassroomResponse> getById(
			@ApiParam(name = "withStudents", required = false, value = "With Students") @RequestParam(name = "withStudents", required = false) boolean withStudents,
			@ApiParam(name = "id", required = true, value = "Class Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		return ResponseEntity.ok(classroomService.getById(id, withStudents));
	}
}
