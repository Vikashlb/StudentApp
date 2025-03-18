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
public class GetStudentByDepartmentNameTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void shouldReturnRecord_WhenDepartmentNameExists(){
        List<Student> students = Arrays.asList(
                new Student("22MX228", "Sanjay", "Krishna",
                        "Computer Science", 84.30, LocalDate.parse("2002-06-10")),
                new Student("22MX221", "Santhosh", "Kumar",
                        "Computer Applications", 90.30, LocalDate.parse("2001-08-21"))
        );

        Mockito.when(studentRepository.findByDepartmentNameLikeIgnoreCase("Computer"+"%")).thenReturn(students);

        List<Student> result = studentService.getStudentByDepartmentName("Computer");

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Computer Science", result.getFirst().getDepartmentName());
        Assertions.assertEquals("Computer Applications", result.getLast().getDepartmentName());
        Mockito.verify(studentRepository,Mockito.times(1)).findByDepartmentNameLikeIgnoreCase("Computer"+"%");
    }

    @Test
    void shouldThrowException_WhenDepartmentNameDoesNotExist() {
        Mockito.when(studentRepository.findByDepartmentNameLikeIgnoreCase("Computer"+"%")).thenThrow(StudentNotFoundException.class);
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudentByDepartmentName("Computer"));
        Mockito.verify(studentRepository,Mockito.times(1)).findByDepartmentNameLikeIgnoreCase("Computer"+"%");
    }
}
