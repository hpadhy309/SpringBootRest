package com.example.SpringBootRest.serviceImpl;

import com.example.SpringBootRest.StudentService;
import com.example.SpringBootRest.entity.Student;
import com.example.SpringBootRest.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override

    public Student createStudent( Student student) {
      Student saveStudent=  studentRepository.save(student);
        return saveStudent;
    }

    @Override
    public Student updateStudent(Student student) {
       Student updateStudent = studentRepository.findById(student.getId()).get();
        updateStudent.setName(student.getName());
        updateStudent.setEmail(student.getEmail());
        updateStudent.setPhone(student.getPhone());
        studentRepository.save(updateStudent);
        return updateStudent;
    }

    @Override
    public Student getStudentById(Long sid) {
        Optional<Student> optionalUser = studentRepository.findById(sid);
        return optionalUser.get();
    }





    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
}
