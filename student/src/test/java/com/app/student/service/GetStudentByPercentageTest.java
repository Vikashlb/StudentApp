package com.app.student.service;

import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.repository.StudentRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
public class GetStudentByPercentageTest {
  @Mock private StudentRepository studentRepository;

  @InjectMocks private StudentServiceImpl studentService;

  @Test
  void shouldReturnRecord_WhenPercentageExists() {
    List<Student> students =
        Arrays.asList(
            new Student(
                "22MX228",
                "Sanjay",
                "Krishna",
                "Computer Science",
                85.45,
                LocalDate.parse("2002-06-10")),
            new Student(
                "22MX221",
                "Santhosh",
                "Kumar",
                "Mechanical",
                85.45,
                LocalDate.parse("2001-08-21")));

    Mockito.when(studentRepository.findByPercentageEquals(85.45)).thenReturn(students);

    List<Student> result = studentService.getStudentByPercentage(85.45);

    Assertions.assertFalse(result.isEmpty());
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals(85.45, result.getFirst().getPercentage());
    Assertions.assertEquals(85.45, result.getLast().getPercentage());
    Mockito.verify(studentRepository, Mockito.times(1)).findByPercentageEquals(85.45);
  }

  @Test
  void shouldThrowException_WhenDepartmentNameDoesNotExist() {
    Mockito.when(studentRepository.findByPercentageEquals(85.45))
        .thenThrow(StudentNotFoundException.class);
    Assertions.assertThrows(
        StudentNotFoundException.class, () -> studentService.getStudentByPercentage(85.45));
    Mockito.verify(studentRepository, Mockito.times(1)).findByPercentageEquals(85.45);
  }
}
