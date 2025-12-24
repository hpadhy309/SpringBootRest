package com.example.SpringBootRest.repository;

import com.example.SpringBootRest.entity.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class StudentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    private Student student1;
    private Student student2;

    @BeforeEach
    void setUp() {
        // Clear any existing data first
        studentRepository.deleteAll();
        entityManager.flush();
        
        // Initialize test data
        student1 = Student.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();

        student2 = Student.builder()
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .phone("0987654321")
                .build();
                
        // Save test data to in-memory database
        student1 = entityManager.persist(student1);
        student2 = entityManager.persist(student2);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void whenFindById_thenReturnStudent() {
        // when
        Optional<Student> found = studentRepository.findById(student1.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(student1.getName());
        assertThat(found.get().getEmail()).isEqualTo(student1.getEmail());
        assertThat(found.get().getPhone()).isEqualTo(student1.getPhone());
    }

    @Test
    void whenFindAll_thenReturnAllStudents() {
        // when
        List<Student> students = studentRepository.findAll();

        // then
        assertThat(students).hasSize(2);
        assertThat(students).extracting(Student::getName)
                .containsExactlyInAnyOrder(student1.getName(), student2.getName());
    }

    @Test
    void whenSave_thenSaveAndReturnSavedStudent() {
        // given
        Student newStudent = Student.builder()
                .name("New Student")
                .email("new.student@example.com")
                .phone("5555555555")
                .build();

        // when
        Student savedStudent = studentRepository.save(newStudent);

        // then
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getName()).isEqualTo(newStudent.getName());
        assertThat(studentRepository.findById(savedStudent.getId())).isPresent();
    }

    @Test
    void whenDelete_thenRemoveStudent() {
        // when
        studentRepository.delete(student1);

        // then
        assertThat(studentRepository.findById(student1.getId())).isEmpty();
        assertThat(studentRepository.findAll()).hasSize(1);
    }

    @Test
    void whenUpdate_thenReturnUpdatedStudent() {
        // given
        Student studentToUpdate = studentRepository.findById(student1.getId()).get();
        studentToUpdate.setName("Updated Name");
        studentToUpdate.setEmail("updated.email@example.com");

        // when
        Student updatedStudent = studentRepository.save(studentToUpdate);

        // then
        assertThat(updatedStudent.getName()).isEqualTo("Updated Name");
        assertThat(updatedStudent.getEmail()).isEqualTo("updated.email@example.com");
        
        Student studentFromDb = studentRepository.findById(student1.getId()).get();
        assertThat(studentFromDb.getName()).isEqualTo("Updated Name");
    }
}
