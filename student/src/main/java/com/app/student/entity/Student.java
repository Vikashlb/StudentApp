package com.app.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

  @Id
  @NotBlank(message = "University Number cannot be empty!")
  @Pattern(
      regexp = "^[A-Za-z0-9]+$",
      message = "University number must contain only alphanumeric characters!")
  @Column(name = "university_number")
  private String universityNumber;

  @NotBlank(message = "First name cannot be empty!")
  @Pattern(
      regexp = "^[a-zA-Z]+$",
      message = "Student's firstname must contain only alphabets with no blank spaces")
  @Column(name = "first_name")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty!")
  @Pattern(
      regexp = "^[a-zA-Z]+$",
      message = "Student's lastname must contain only alphabets with no blank spaces")
  @Column(name = "last_name")
  private String lastName;

  @NotBlank(message = "Department name cannot be empty!")
  @Column(name = "department_name")
  private String departmentName;

  @Min(value = 1, message = "Percentage must be greater than zero!")
  @Column(name = "percentage")
  private double percentage;

  @NotNull(message = "Date of Birth cannot be empty!")
  @PastOrPresent(message = "Date of Birth must only be in past or present!")
  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;
}
