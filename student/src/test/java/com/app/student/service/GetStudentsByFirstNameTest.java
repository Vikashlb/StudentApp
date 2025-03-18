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
public class GetStudentsByFirstNameTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldReturnRecord_WhenNameExists(){
        List<Student> students = Arrays.asList(
                new Student("22MX228", "Sanjay", "Krishna",
                        "Computer Applications", 84.30, LocalDate.parse("2002-06-10")),
                new Student("22MX221", "Santhosh", "Kumar",
                        "Computer Applications", 90.30, LocalDate.parse("2001-08-21"))
        );

        Mockito.when(studentRepository.findByFirstNameLikeIgnoreCase("San"+"%")).thenReturn(students);

        List<Student> result = studentService.getStudentsByFirstName("San");

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Sanjay", result.getFirst().getFirstName());
        Assertions.assertEquals("Santhosh", result.getLast().getFirstName());
    }

    @Test
    void shouldThrowException_WhenNameDoesNotExist() {
        Mockito.when(studentRepository.findByFirstNameLikeIgnoreCase("San"+"%")).thenThrow(StudentNotFoundException.class);
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudentsByFirstName("San"));
    }
}
