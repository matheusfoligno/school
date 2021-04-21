package com.course.school.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.course.school.dto.requests.StudentRequest;
import com.course.school.dto.responses.StudentResponse;
import com.course.school.entities.Student;
import com.course.school.exceptions.NotFoundException;
import com.course.school.repositories.StudentRepository;

@Service
public class StudentService {

	private static final String FAILED_TO_GET_BY_ID = "Failed to get by id %s";

	@Autowired
	private StudentRepository studentRepository;

	@Transactional
	public Student insert(StudentRequest request) {
		Student student = StudentRequest.toDomain(request, null);
		return studentRepository.save(student);
	}

	@Transactional
	public void update(StudentRequest request, Long id) throws NotFoundException {
		findById(id);

		studentRepository.save(StudentRequest.toDomain(request, id));
	}

	@Transactional
	public void delete(Long id) throws NotFoundException {
		Student student = findById(id);

		studentRepository.delete(student);
	}

	public Page<StudentResponse> getAll(Pageable pageable, boolean withClassrooms) {
		return studentRepository.findAll(pageable).map(s -> StudentResponse.toResponse(s, withClassrooms));
	}

	public StudentResponse getById(Long id, boolean withClassrooms) throws NotFoundException {
		return StudentResponse.toResponse(findById(id), withClassrooms);
	}

	public Student findById(Long id) throws NotFoundException {
		return studentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(FAILED_TO_GET_BY_ID, id)));
	}

}
