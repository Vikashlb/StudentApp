package com.app.student.service;

import com.app.student.entity.Student;
import java.util.List;

public interface StudentService {
  Student addStudent(Student student);

  Student getStudentById(String univNumber);

  List<Student> getAllStudents();

  Student updateStudent(Student student, String univNumber);

  String deleteStudent(String univNumber);

  List<Student> getStudentsByFirstName(String name);

  List<Student> getStudentByDepartmentName(String depName);

  List<Student> getStudentByPercentage(double percent);
}
