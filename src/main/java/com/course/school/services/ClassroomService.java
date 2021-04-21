package com.course.school.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.course.school.dto.requests.ClassroomRequest;
import com.course.school.dto.responses.ClassroomResponse;
import com.course.school.entities.Classroom;
import com.course.school.exceptions.NotFoundException;
import com.course.school.repositories.ClassroomRepository;

@Service
public class ClassroomService {

	private static final String FAILED_TO_GET_BY_ID = "Failed to get by id %s";

	@Autowired
	private ClassroomRepository classRepository;

	@Transactional
	public Classroom insert(ClassroomRequest request) {
		Classroom classroom = ClassroomRequest.toDomain(request, null);
		return classRepository.save(classroom);
	}

	@Transactional
	public void update(ClassroomRequest request, Long id) throws NotFoundException {
		findById(id);

		classRepository.save(ClassroomRequest.toDomain(request, id));
	}

	@Transactional
	public void delete(Long id) throws NotFoundException {
		Classroom classroom = findById(id);

		classRepository.delete(classroom);
	}

	public Page<ClassroomResponse> getAll(Pageable pageable, boolean withStudents) {
		return classRepository.findAll(pageable).map(c -> ClassroomResponse.toResponse(c, withStudents));
	}

	public ClassroomResponse getById(Long id, boolean withStudents) throws NotFoundException {
		return ClassroomResponse.toResponse(findById(id), withStudents);
	}

	public Classroom findById(Long id) throws NotFoundException {
		return classRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format(FAILED_TO_GET_BY_ID, id)));
	}

}
