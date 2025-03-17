package com.app.student.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

@Entity
public class Student {

    @Id
    @NotBlank(message = "University Number cannot be empty!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "University number must contain only alphanumeric characters!")
    private String universityNumber;

    @NotBlank(message = "First name cannot be empty!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Student's firstname must contain only alphabets with no blank spaces")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Student's lastname must contain only alphabets with no blank spaces")
    private String lastName;

    @NotBlank(message = "Department name cannot be empty!")
    private String departmentName;

    @Min(value = 1, message = "Percentage must be greater than zero!")
    private double percentage;

    @NotNull(message = "Date of Birth cannot be empty!")
    @PastOrPresent(message = "Date of Birth must only be in past or present!")
    private LocalDate dateOfBirth;

    //DefaultConstructor
    public Student(){
        
    }
    
    //ParamConstructor
    public Student(String univNumber, String fName, String lName, String depName, double percent, LocalDate dob) {
        this.universityNumber = univNumber;
        this.firstName = fName;
        this.lastName = lName;
        this.departmentName = depName;
        this.percentage = percent;
        this.dateOfBirth = dob;
    }

    //Getters
    public String getUniversityNumber() {
        return universityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public double getPercentage() {
        return percentage;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    //Setters
    public void setUniversityNumber(String universityNumber) {
        this.universityNumber = universityNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
