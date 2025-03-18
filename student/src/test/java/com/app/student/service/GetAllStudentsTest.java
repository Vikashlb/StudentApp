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
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GetAllStudentsTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldReturnStudentList_WhenRecordsAreAvailable() {
        List<Student> list = Arrays.asList(
                new Student("22MX228", "Vikash", "Bojarajan",
                "Computer Applications", 84.30, LocalDate.parse("2002-06-10")),
                new Student("22MX221", "Santhosh", "Kumar",
                        "Computer Applications", 90.30, LocalDate.parse("2001-08-21"))
        );

        Mockito.when(studentRepository.findAll()).thenReturn(list);

        List<Student> result = studentService.getAllStudents();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals("22MX228", result.getFirst().getUniversityNumber());
        Assertions.assertEquals("22MX221", result.getLast().getUniversityNumber());
    }

    @Test
    void shouldThrowException_WhenNoRecordsAreAvailable() {

        Mockito.when(studentRepository.findAll()).thenThrow(StudentNotFoundException.class);

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getAllStudents());
    }
}
