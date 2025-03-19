package com.app.student.controller;

import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = StudentController.class)
@ExtendWith(MockitoExtension.class)
public class GetStudentByIdTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentServiceImpl studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private String universityNumber;

    @BeforeEach
    void init() {
        universityNumber = "22MX228";
        student = new Student("22MX228", "Sanjay", "Krishna",
                "Computer Applications", 84.30, LocalDate.parse("2002-06-10"));
    }

    @Test
    void ShouldReturnOk_WhenIdExists() throws Exception {
        given(studentService.getStudentById(ArgumentMatchers.any())).willReturn(student);
        ResultActions response = mockMvc.perform(get("/getStudentById/{id}",universityNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.universityNumber").value(student.getUniversityNumber()));
    }

    @Test
    void ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        given(studentService.getStudentById(ArgumentMatchers.any())).willThrow(new StudentNotFoundException("Student Record Not Found!"));
        ResultActions response = mockMvc.perform(get("/getStudentById/{id}", universityNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.errorMessage").value("Student Record Not Found!"));
    }
}
