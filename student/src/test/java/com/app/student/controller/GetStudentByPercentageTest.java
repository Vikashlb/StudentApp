package com.app.student.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

@WebMvcTest(controllers = StudentController.class)
@ExtendWith(MockitoExtension.class)
public class GetStudentByPercentageTest {
  @Autowired private MockMvc mockMvc;

  @MockitoBean private StudentServiceImpl studentService;

  @Autowired private ObjectMapper objectMapper;

  private double percentage;
  private List<Student> students;

  @BeforeEach
  void init() {
    percentage = 87.50;
    students =
        Arrays.asList(
            new Student(
                "22MX228",
                "Vikash",
                "Bojarajan",
                "Computer Applications",
                87.50,
                LocalDate.parse("2002-06-10")),
            new Student(
                "22MX221",
                "Vikram",
                "Kumar",
                "Computer Applications",
                87.50,
                LocalDate.parse("2001-08-21")));
  }

  @Test
  void ShouldReturnOk_WhenDataIsAvailable() throws Exception {
    given(studentService.getStudentByPercentage(ArgumentMatchers.anyDouble())).willReturn(students);
    ResultActions response =
        mockMvc.perform(
            get("/getStudentByPercentage/{percentage}", percentage)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(students)));

    response
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$[0].percentage").value(87.50))
        .andExpect(jsonPath("$[1].percentage").value(87.50));
  }

  @Test
  void ShouldReturnNotFound_WhenDataIsNotAvailable() throws Exception {
    given(studentService.getStudentByPercentage(ArgumentMatchers.anyDouble()))
        .willThrow(
            new StudentNotFoundException(
                "Student Record With Percentage : '" + percentage + "' Not Found!"));
    ResultActions response =
        mockMvc.perform(
            get("/getStudentByPercentage/{percentage}", percentage)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(students)));

    response
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(
            jsonPath("$.errorMessage")
                .value("Student Record With Percentage : '" + percentage + "' Not Found!"));
  }
}
