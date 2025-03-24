package com.app.student.service;

import com.app.student.entity.Student;
import com.app.student.exception.DuplicateRecordException;
import com.app.student.repository.StudentRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AddStudentTest {
  @Mock private StudentRepository studentRepository;

  @InjectMocks private StudentServiceImpl studentService;

  @Test
  void ShouldReturnStudent_WhenStudentRecordDoesNotExist() {
    Student student =
        new Student(
            "22MX228",
            "Sanjay",
            "Krishna",
            "Computer Science",
            84.30,
            LocalDate.parse("2002-06-10"));

    Mockito.when(studentRepository.existsById("22MX228")).thenReturn(false);

    Student result = studentService.addStudent(student);

    Assertions.assertEquals(student, result);
    Mockito.verify(studentRepository, Mockito.times(1)).save(student);
  }

  @Test
  void ShouldThrowException_WhenRecordExists() {
    Student student =
        new Student(
            "22MX228",
            "Sanjay",
            "Krishna",
            "Computer Science",
            84.30,
            LocalDate.parse("2002-06-10"));
    Mockito.when(studentRepository.existsById("22MX228")).thenReturn(true);

    Assertions.assertThrows(
        DuplicateRecordException.class, () -> studentService.addStudent(student));
    Mockito.verify(studentRepository, Mockito.never()).save(student);
  }
}
