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

import com.course.school.dto.requests.ClassroomRequest;
import com.course.school.dto.responses.ClassroomResponse;
import com.course.school.entities.Classroom;
import com.course.school.exceptions.NotFoundException;
import com.course.school.repositories.ClassroomRepository;
import com.course.school.services.ClassroomService;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceTest {

	@InjectMocks
	private ClassroomService classroomService;

	@Mock
	private ClassroomRepository classroomRepository;

	private ClassroomRequest request = ClassroomRequest.builder().name("NAME").dayOfWeek("MONDAY").build();

	private Classroom classroom = Classroom.builder().id(1l).name("NAME").dayOfWeek("MONDAY").build();

	private Pageable pageable;

	List<Classroom> classroomList = null;
	private Page<Classroom> classroomPage = null;

	@BeforeEach
	public void setUp() {
		pageable = PageRequest.of(0, 2);
		classroomList = new ArrayList<>();
	}

	@Test
	public void shouldInsertWithSuccess() {
		classroomService.insert(request);
	}

	@Test
	public void shouldUpdateWithSuccess() throws NotFoundException {
		when(classroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(classroom));
		classroomService.update(request, 1l);
	}

	@Test
	public void shouldUpdateWithoutClassroomNotFoundException() throws NotFoundException {
		when(classroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> classroomService.update(request, 1l));
	}

	@Test
	public void shouldDeleteWithSuccess() throws NotFoundException {
		when(classroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(classroom));
		classroomService.delete(1l);
	}

	@Test
	public void shouldDeleteWithoutClassroomNotFoundException() throws NotFoundException {
		when(classroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> classroomService.delete(1l));
	}

	@Test
	public void shouldGetByIdWithSuccess() throws NotFoundException {
		when(classroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(classroom));
		ClassroomResponse response = classroomService.getById(1l, false);
		assertEquals(1l, response.getId());
	}

	@Test
	public void shouldGetByIdWithoutClassroomNotFoundException() throws NotFoundException {
		when(classroomRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> classroomService.getById(1l, false));
	}

	@Test
	public void shouldGetAllWithSuccess() throws NotFoundException {
		classroomList.add(classroom);
		classroomPage = new PageImpl<Classroom>(classroomList);
		when(classroomRepository.findAll(Mockito.any(Pageable.class))).thenReturn(classroomPage);
		Page<ClassroomResponse> response = classroomService.getAll(pageable, false);
		assertEquals(1, response.getSize());
		assertEquals("NAME", response.getContent().iterator().next().getName());
	}

}
