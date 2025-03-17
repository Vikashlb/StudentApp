package com.app.student.service;

import com.app.student.entity.Student;

import java.util.List;

public interface StudentService {
    public Student addStudent(Student student);

    public Student getStudentById(String univNumber);

    public List<Student> getAllStudents();

    public Student updateStudent(Student student, String univNumber);

    public String deleteStudent(String univNumber);

    public List<Student> getStudentsByFirstName(String name); //, String department, double percentage);

    public List<Student> getStudentByDepartmentName(String depName);

    public List<Student> getStudentByPercentage(double percent);
}
