package com.example.SpringBootRest.controller;

import com.example.SpringBootRest.StudentService;
import com.example.SpringBootRest.entity.Product;
import com.example.SpringBootRest.entity.Student;
import com.example.SpringBootRest.serviceImpl.StudentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
    private StudentServiceImpl studentService;



   @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student)
    {
     Student saveStudent= studentService.createStudent(student);
     return new ResponseEntity<>(saveStudent, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> student = studentService.getAllStudent();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long studentId){
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long studentId, @RequestBody Student student){
       student.setId(studentId) ;
       Student updateStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }


}
