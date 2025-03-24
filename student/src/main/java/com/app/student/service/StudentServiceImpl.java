package com.app.student.service;

import com.app.student.entity.Student;
import com.app.student.exception.DuplicateRecordException;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.repository.StudentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  @Autowired private StudentRepository studentRepository;

  @Override
  public Student addStudent(Student student) {
    if (!studentRepository.existsById(student.getUniversityNumber())) {
      studentRepository.save(student);
    } else {
      throw new DuplicateRecordException("Student Record Already Exists!");
    }
    return student;
  }

  @Override
  public Student getStudentById(String univNumber) {
    return studentRepository
        .findById(univNumber)
        .orElseThrow(() -> new StudentNotFoundException("Student Record Not Found!"));
  }

  @Override
  public List<Student> getAllStudents() {
    List<Student> list = (List<Student>) studentRepository.findAll();
    if (list.isEmpty()) {
      throw new StudentNotFoundException("No Student Records Available!");
    }
    return list;
  }

  @Override
  public Student updateStudent(Student student, String univNumber) {
    Student updatedStudent;
    if (studentRepository.existsById(univNumber)) {
      student.setFirstName(student.getFirstName());
      student.setLastName(student.getLastName());
      student.setDepartmentName(student.getDepartmentName());
      student.setPercentage(student.getPercentage());
      student.setDateOfBirth(student.getDateOfBirth());
      updatedStudent = studentRepository.save(student);
    } else {
      throw new StudentNotFoundException("Student Record Doesn't Exist!");
    }
    return updatedStudent;
  }

  @Override
  public String deleteStudent(String univNumber) {
    if (!studentRepository.existsById(univNumber)) {
      throw new StudentNotFoundException("Student Record Doesn't Exist!");
    }
    studentRepository.deleteById(univNumber);
    return "Student Record Successfully Deleted";
  }

  @Override
  public List<Student> getStudentsByFirstName(String name) {
    List<Student> students = studentRepository.findByFirstNameLikeIgnoreCase(name + "%");
    if (students.isEmpty()) {
      throw new StudentNotFoundException("Student Record With Name : '" + name + "' Not Found!");
    }
    return students;
  }

  @Override
  public List<Student> getStudentByDepartmentName(String depName) {
    List<Student> students = studentRepository.findByDepartmentNameLikeIgnoreCase(depName + "%");
    if (students.isEmpty()) {
      throw new StudentNotFoundException(
          "Student Record With Department : '" + depName + "' Not Found!");
    }
    return students;
  }

  @Override
  public List<Student> getStudentByPercentage(double percent) {
    List<Student> students = studentRepository.findByPercentageEquals(percent);
    if (students.isEmpty()) {
      throw new StudentNotFoundException(
          "Student Record With Percentage : '" + percent + "' Not Found!");
    }
    return students;
  }
}
