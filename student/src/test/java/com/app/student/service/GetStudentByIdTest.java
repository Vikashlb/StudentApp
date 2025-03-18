package com.app.student.service;

import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GetStudentByIdTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldReturnStudent_WhenIdIsValid() {
        Student student = new Student("22MX228", "Vikash", "Bojarajan",
                "Computer Applications", 85.2, LocalDate.parse( "2002-06-10"));

        Mockito.when(studentRepository.findById("22MX228")).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById("22MX228");

        Assertions.assertNotNull(result);
        Assertions.assertEquals("22MX228", result.getUniversityNumber());
        Assertions.assertEquals("Vikash", result.getFirstName());
        Assertions.assertEquals("Bojarajan", result.getLastName());
        Assertions.assertEquals("Computer Applications", result.getDepartmentName());
        Assertions.assertEquals(85.20, result.getPercentage());
        Assertions.assertEquals(LocalDate.parse("2002-06-10"), result.getDateOfBirth());
        Mockito.verify(studentRepository,Mockito.times(1)).findById("22MX228");
    }

    @Test
    void ShouldThrowException_WhenIdIsInvalid() {
        Mockito.when(studentRepository.findById("22MX228")).thenThrow(StudentNotFoundException.class);
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById("22MX228"));
        Mockito.verify(studentRepository,Mockito.times(1)).findById("22MX228");
    }
}
