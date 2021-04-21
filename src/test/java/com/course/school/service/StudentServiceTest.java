package com.course.school.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.course.school.dto.requests.StudentRequest;
import com.course.school.dto.responses.StudentResponse;
import com.course.school.entities.Student;
import com.course.school.exceptions.NotFoundException;
import com.course.school.repositories.StudentRepository;
import com.course.school.services.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@InjectMocks
	private StudentService studentService;

	@Mock
	private StudentRepository studentRepository;

	private StudentRequest request = StudentRequest.builder().age(1).name("NAME").build();

	private Student student = Student.builder().id(1l).age(1).name("NAME").build();

	private Pageable pageable;

	List<Student> studentList = null;
	private Page<Student> studentPage = null;

	@BeforeEach
	public void setUp() {
		pageable = PageRequest.of(0, 2);
		studentList = new ArrayList<>();
	}

	@Test
	public void shouldInsertWithSuccess() {
		studentService.insert(request);
	}

	@Test
	public void shouldUpdateWithSuccess() throws NotFoundException {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
		studentService.update(request, 1l);
	}

	@Test
	public void shouldUpdateWithoutStudentNotFoundException() throws NotFoundException {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> studentService.update(request, 1l));
	}

	@Test
	public void shouldDeleteWithSuccess() throws NotFoundException {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
		studentService.delete(1l);
	}

	@Test
	public void shouldDeleteWithoutStudentNotFoundException() throws NotFoundException {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> studentService.delete(1l));
	}

	@Test
	public void shouldGetByIdWithSuccess() throws NotFoundException {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(student));
		StudentResponse response = studentService.getById(1l, false);
		assertEquals(1l, response.getId());
	}

	@Test
	public void shouldGetByIdWithoutStudentNotFoundException() throws NotFoundException {
		when(studentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> studentService.getById(1l, false));
	}

	@Test
	public void shouldGetAllWithSuccess() throws NotFoundException {
		studentList.add(student);
		studentPage = new PageImpl<Student>(studentList);
		when(studentRepository.findAll(Mockito.any(Pageable.class))).thenReturn(studentPage);
		Page<StudentResponse> response = studentService.getAll(pageable, false);
		assertEquals(1, response.getSize());
		assertEquals("NAME", response.getContent().iterator().next().getName());
	}

}
