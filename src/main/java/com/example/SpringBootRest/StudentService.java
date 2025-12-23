package com.example.SpringBootRest;

import com.example.SpringBootRest.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student updateStudent(Student student);
     Student getStudentById(Long sid);

    List<Student> getAllStudent();

    void deleteStudent(long id);
}
