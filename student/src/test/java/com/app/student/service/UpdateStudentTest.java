package com.app.student.service;

import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
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
public class UpdateStudentTest {
  @Mock private StudentRepository studentRepository;

  @InjectMocks private StudentServiceImpl studentService;

  @Test
  void ShouldReturnStudent_WhenIdExists() {
    Student student =
        new Student(
            "22MX228",
            "Sanjay",
            "Krishna",
            "Computer Science",
            84.30,
            LocalDate.parse("2002-06-10"));
    Mockito.when(studentRepository.existsById("22MX228")).thenReturn(true);
    Mockito.when(studentRepository.save(student)).thenReturn(student);

    Student result = studentService.updateStudent(student, "22MX228");

    Assertions.assertNotNull(result);
    Assertions.assertEquals(student, result);

    Mockito.verify(studentRepository).existsById("22MX228");
    Mockito.verify(studentRepository, Mockito.times(1)).save(student);
  }

  @Test
  void ShouldThrowException_WhenIdDoesNotExist() {
    Student student =
        new Student(
            "22MX228",
            "Sanjay",
            "Krishna",
            "Computer Science",
            84.30,
            LocalDate.parse("2002-06-10"));
    Mockito.when(studentRepository.existsById("22MX228")).thenReturn(false);
    Assertions.assertThrows(
        StudentNotFoundException.class, () -> studentService.updateStudent(student, "22MX228"));
    Mockito.verify(studentRepository, Mockito.never()).save(student);
  }
}
