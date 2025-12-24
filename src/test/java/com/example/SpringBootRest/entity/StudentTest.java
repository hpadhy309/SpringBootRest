package com.example.SpringBootRest.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {

    private Student student;
    private final Long ID = 1L;
    private final String NAME = "John Doe";
    private final String EMAIL = "john.doe@example.com";
    private final String PHONE = "1234567890";

    @BeforeEach
    void setUp() {
        // Initialize a student object before each test
        student = Student.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .phone(PHONE)
                .build();
    }

    @Test
    void studentBuilder_ShouldCreateStudentWithAllFields() {
        // Verify all fields are set correctly through the builder
        assertThat(student).isNotNull();
        assertThat(student.getId()).isEqualTo(ID);
        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getEmail()).isEqualTo(EMAIL);
        assertThat(student.getPhone()).isEqualTo(PHONE);
    }

    @Test
    void studentGettersAndSetters_ShouldWorkCorrectly() {
        // Test getters and setters
        Long newId = 2L;
        String newName = "Jane Smith";
        String newEmail = "jane.smith@example.com";
        String newPhone = "0987654321";

        student.setId(newId);
        student.setName(newName);
        student.setEmail(newEmail);
        student.setPhone(newPhone);

        assertThat(student.getId()).isEqualTo(newId);
        assertThat(student.getName()).isEqualTo(newName);
        assertThat(student.getEmail()).isEqualTo(newEmail);
        assertThat(student.getPhone()).isEqualTo(newPhone);
    }

//    @Test
//    void studentEqualsAndHashCode_ShouldWorkCorrectly() {
//        // Create a student with the same ID
//        Student sameStudent = Student.builder()
//                .id(ID)
//                .name(NAME)
//                .email(EMAIL)
//                .phone(PHONE)
//                .build();
//
//        // Create a different student
//        Student differentStudent = Student.builder()
//                .id(999L)
//                .name("Different Name")
//                .email("different@example.com")
//                .phone("0000000000")
//                .build();
//
//        // Test equals and hashCode
//        assertThat(student).isEqualTo(sameStudent);
//        assertThat(student).isNotSameAs(sameStudent);
//        assertThat(student).isNotEqualTo(differentStudent);
//        assertThat(student.hashCode()).isEqualTo(sameStudent.hashCode());
//        assertThat(student.hashCode()).isNotEqualTo(differentStudent.hashCode());
//    }

//    @Test
//    void studentToString_ShouldContainAllFields() {
//        String studentString = student.toString();
//
//        // Check if the string contains all the field values
//        assertThat(studentString).contains(NAME, EMAIL, PHONE);
//        // Since ID is generated, we'll just check if the string is not empty
//        assertThat(studentString).isNotEmpty();
//    }

    @Test
    void studentNoArgsConstructor_ShouldCreateEmptyStudent() {
        // Test no-args constructor
        Student emptyStudent = new Student();
        
        assertThat(emptyStudent).isNotNull();
        assertThat(emptyStudent.getId()).isNull();
        assertThat(emptyStudent.getName()).isNull();
        assertThat(emptyStudent.getEmail()).isNull();
        assertThat(emptyStudent.getPhone()).isNull();
    }

    @Test
    void studentAllArgsConstructor_ShouldCreateStudentWithAllFields() {
        // Test all-args constructor
        Student allArgsStudent = new Student(ID, NAME, EMAIL, PHONE);
        
        assertThat(allArgsStudent).isNotNull();
        assertThat(allArgsStudent.getId()).isEqualTo(ID);
        assertThat(allArgsStudent.getName()).isEqualTo(NAME);
        assertThat(allArgsStudent.getEmail()).isEqualTo(EMAIL);
        assertThat(allArgsStudent.getPhone()).isEqualTo(PHONE);
    }
}
