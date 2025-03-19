package com.app.student.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = StudentController.class)
@ExtendWith(MockitoExtension.class)
public class GetAllStudentsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentServiceImpl studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Student> students;

    @BeforeEach
    void init() {
        students = Arrays.asList(
                new Student("22MX228", "Vikash", "Bojarajan",
                        "Computer Applications", 84.30, LocalDate.parse("2002-06-10")),
                new Student("22MX221", "Santhosh", "Kumar",
                        "Computer Applications", 90.30, LocalDate.parse("2001-08-21"))
        );
    }

    @Test
    void ShouldReturnOk_WhenRecordsAreAvailable() throws Exception {
        given(studentService.getAllStudents()).willReturn(students);
        ResultActions response = mockMvc.perform(get("/getAllStudents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(students)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].universityNumber").value("22MX228"))
                .andExpect(jsonPath("$[1].universityNumber").value("22MX221"));
    }

    @Test
    void ShouldReturnNotFound_WhenNoRecordsAreAvailable() throws Exception {
        given(studentService.getAllStudents()).willThrow(new StudentNotFoundException("No Student Records Available!"));
        ResultActions response = mockMvc.perform(get("/getAllStudents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(students)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.errorMessage").value("No Student Records Available!"));
    }
}
