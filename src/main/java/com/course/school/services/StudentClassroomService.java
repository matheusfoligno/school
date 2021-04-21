package com.course.school.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.course.school.entities.Classroom;
import com.course.school.entities.Student;
import com.course.school.entities.StudentClassroom;
import com.course.school.enums.Status;
import com.course.school.exceptions.BadRequestException;
import com.course.school.exceptions.ConflictException;
import com.course.school.exceptions.NotFoundException;
import com.course.school.repositories.StudentClassroomRepository;

@Service
public class StudentClassroomService {

	private static final String DATE_ALREADY_SAVED = "Date already saved with this student id and classroom id";
	private static final String IS_INACTIVE = "%s is inactive";
	private static final String FAILED_TO_GET_BY_ID = "Failed to get by id %s";

	@Autowired
	private StudentService studentService;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private StudentClassroomRepository studentClassroomRepository;

	@Transactional
	public StudentClassroom insert(Long studentId, Long classroomId)
			throws NotFoundException, BadRequestException, ConflictException {
		Classroom classroom = classroomService.findById(classroomId);
		Student student = studentService.findById(studentId);

		if (studentClassroomRepository.existsByStudentAndClassroom(student, classroom)) {
			throw new ConflictException(DATE_ALREADY_SAVED);
		}

		if (student.getStatus().equals(Status.INACTIVE)) {
			throw new BadRequestException(String.format(IS_INACTIVE, student.getName()));
		}

		return studentClassroomRepository
				.save(StudentClassroom.builder().classroom(classroom).student(student).build());
	}

	@Transactional
	public void delete(Long id) throws NotFoundException {
		StudentClassroom entity = studentClassroomRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(FAILED_TO_GET_BY_ID));
		studentClassroomRepository.delete(entity);

	}

}
