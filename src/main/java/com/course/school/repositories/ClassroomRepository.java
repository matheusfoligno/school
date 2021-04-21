package com.course.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.school.entities.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

}
