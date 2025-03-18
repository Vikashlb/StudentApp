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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeleteStudentTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldDeleteRecord_WhenValidIdIsProvided(){
        Student student = new Student("22MX228", "Vikash", "Bojarajan",
                "Computer Applications", 84.30, LocalDate.parse("2002-06-10"));
        Mockito.when(studentRepository.existsById("22MX228")).thenReturn(true);
        Mockito.doNothing().when(studentRepository).deleteById("22MX228");

        String result = studentService.deleteStudent("22MX228");

        Assertions.assertEquals("Student Record Successfully Deleted", result);
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(student.getUniversityNumber());
    }

    @Test
    void shouldThrowException_WhenIdDoesNotExist(){
        Mockito.when(studentRepository.existsById("22MX228")).thenThrow(StudentNotFoundException.class);

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent("22MX228"));
    }
}
