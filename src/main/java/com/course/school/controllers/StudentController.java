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

import com.course.school.dto.requests.StudentRequest;
import com.course.school.dto.responses.StudentResponse;
import com.course.school.entities.Student;
import com.course.school.exceptions.NotFoundException;
import com.course.school.services.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/api/students")
@Api(value = "Students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@ApiOperation(value = "Insert a student", httpMethod = "POST")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<String> insert(@RequestBody @Valid StudentRequest request) {
		Student student = studentService.insert(request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(student.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation(value = "Update a student", httpMethod = "PUT")
	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> update(@RequestBody @Valid StudentRequest request,
			@ApiParam(name = "id", required = true, value = "Student Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		studentService.update(request, id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Delete a student", httpMethod = "DELETE")
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(
			@ApiParam(name = "id", required = true, value = "Student Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		studentService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Get all student", httpMethod = "GET")
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Page<StudentResponse>> getAll(
			@ApiParam(name = "withClassrooms", required = false, value = "With Classrooms") @RequestParam(name = "withClassrooms", required = false) boolean withClassrooms,
			Pageable pageable) {
		return ResponseEntity.ok(studentService.getAll(pageable, withClassrooms));
	}

	@ApiOperation(value = "Get by id", httpMethod = "GET")
	@GetMapping("/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<StudentResponse> getById(
			@ApiParam(name = "withClassrooms", required = false, value = "With Classrooms") @RequestParam(name = "withClassrooms", required = false) boolean withClassrooms,
			@ApiParam(name = "id", required = true, value = "Student Id") @PathVariable(name = "id") Long id)
			throws NotFoundException {
		return ResponseEntity.ok(studentService.getById(id, withClassrooms));
	}
}
