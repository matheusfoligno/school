package com.course.school.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.course.school.entities.Classroom;
import com.course.school.entities.Student;
import com.course.school.entities.Student.StudentBuilder;
import com.course.school.entities.StudentClassroom;
import com.course.school.enums.Status;
import com.course.school.exceptions.BadRequestException;
import com.course.school.exceptions.ConflictException;
import com.course.school.exceptions.NotFoundException;
import com.course.school.repositories.StudentClassroomRepository;
import com.course.school.services.ClassroomService;
import com.course.school.services.StudentClassroomService;
import com.course.school.services.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentClassroomServiceTest {

	@InjectMocks
	private StudentClassroomService studentClassroomService;

	@Mock
	private StudentService studentService;

	@Mock
	private ClassroomService classroomService;

	@Mock
	private StudentClassroomRepository studentClassroomRepository;

	private Classroom classroom = Classroom.builder().id(1l).name("NAME").dayOfWeek("MONDAY").build();

	private StudentBuilder student = Student.builder().id(1l).age(1).status(Status.ACTIVE).name("NAME");

	@Test
	public void shouldInsertWithSuccess() throws NotFoundException, BadRequestException, ConflictException {
		when(classroomService.findById(Mockito.anyLong())).thenReturn(classroom);
		when(studentService.findById(Mockito.anyLong())).thenReturn(student.build());
		studentClassroomService.insert(1l, 1l);
	}

	@Test
	public void shouldInsertWithStudentInactiveBadRequest() throws NotFoundException, BadRequestException {
		when(classroomService.findById(Mockito.anyLong())).thenReturn(classroom);
		when(studentService.findById(Mockito.anyLong())).thenReturn(student.status(Status.INACTIVE).build());
		assertThrows(BadRequestException.class, () -> studentClassroomService.insert(1l, 1l));
	}

	@Test
	public void shouldInsertWithoutStudentNotFound() throws NotFoundException, BadRequestException {
		when(classroomService.findById(Mockito.anyLong())).thenReturn(classroom);
		when(studentService.findById(Mockito.anyLong())).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> studentClassroomService.insert(1l, 1l));
	}

	@Test
	public void shouldInsertWithoutClassroomNotFound() throws NotFoundException, BadRequestException {
		when(classroomService.findById(Mockito.anyLong())).thenThrow(NotFoundException.class);
		assertThrows(NotFoundException.class, () -> studentClassroomService.insert(1l, 1l));
	}

	@Test
	public void shouldDeleteWithSuccess() throws NotFoundException, BadRequestException {
		when(studentClassroomRepository.findById(Mockito.anyLong()))
				.thenReturn(Optional.of(StudentClassroom.builder().build()));
		studentClassroomService.delete(1l);
	}

	@Test
	public void shouldDeleteNotFound() throws NotFoundException, BadRequestException {
		when(studentClassroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> studentClassroomService.delete(1l));
	}

}
