package com.course.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.school.entities.Classroom;
import com.course.school.entities.Student;
import com.course.school.entities.StudentClassroom;

@Repository
public interface StudentClassroomRepository extends JpaRepository<StudentClassroom, Long> {

	boolean existsByStudentAndClassroom(Student student, Classroom classroom);

}
