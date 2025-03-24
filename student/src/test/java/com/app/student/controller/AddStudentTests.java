package com.app.student.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.app.student.entity.Student;
import com.app.student.exception.DuplicateRecordException;
import com.app.student.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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
public class AddStudentTests {
  @Autowired private MockMvc mockMvc;

  @MockitoBean private StudentServiceImpl studentService;

  @Autowired private ObjectMapper objectMapper;

  private Student student;

  @BeforeEach
  void init() {
    student =
        new Student(
            "22MX228",
            "Sanjay",
            "Krishna",
            "Computer Applications",
            84.30,
            LocalDate.parse("2002-06-10"));
  }

  @Test
  public void ShouldReturnOk_WhenIdDoesNotExist() throws Exception {
    given(studentService.addStudent(ArgumentMatchers.any())).willReturn(student);
    ResultActions response =
        mockMvc.perform(
            post("/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    response
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.firstName").value("Sanjay"));
  }

  @Test
  public void ShouldReturnConflict_WhenIdAlreadyExists() throws Exception {
    given(studentService.addStudent(ArgumentMatchers.any()))
        .willThrow(new DuplicateRecordException("Student Record Already Exists!"));
    ResultActions response =
        mockMvc.perform(
            post("/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

    response
        .andExpect(MockMvcResultMatchers.status().isConflict())
        .andExpect(jsonPath("$.statusCode").value(409))
        .andExpect(jsonPath("$.errorMessage").value("Student Record Already Exists!"));
  }
}
