package com.example.SpringBootRest.repository;

import com.example.SpringBootRest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository  extends JpaRepository<Student,Long> {
}
