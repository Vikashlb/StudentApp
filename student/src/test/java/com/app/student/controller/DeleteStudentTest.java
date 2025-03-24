package com.app.student.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = StudentController.class)
@ExtendWith(MockitoExtension.class)
public class DeleteStudentTest {
  @Autowired private MockMvc mockMvc;

  @MockitoBean private StudentServiceImpl studentService;

  @Autowired private ObjectMapper objectMapper;

  private String universityNumber;

  @BeforeEach
  void init() {
    universityNumber = "22MX228";
  }

  @Test
  void ShouldReturnOk_WhenIdIsDeleted() throws Exception {
    given(studentService.deleteStudent(ArgumentMatchers.any()))
        .willReturn("Student Record Successfully Deleted");
    ResultActions response = mockMvc.perform(delete("/deleteStudentById/{id}", universityNumber));

    response
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Student Record Successfully Deleted"));
  }

  @Test
  void ShouldReturnNotFound_WhenIdIsNotAvailable() throws Exception {
    given(studentService.deleteStudent(ArgumentMatchers.any()))
        .willThrow(new StudentNotFoundException("Student Record Doesn't Exist!"));
    ResultActions response = mockMvc.perform(delete("/deleteStudentById/{id}", universityNumber));

    response
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.errorMessage").value("Student Record Doesn't Exist!"));
  }
}
