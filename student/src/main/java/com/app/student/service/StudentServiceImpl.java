package com.app.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.student.entity.Student;
import com.app.student.exception.StudentNotFoundException;
import com.app.student.exception.DuplicateRecordException;
import com.app.student.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student addStudent(Student student) {
        if(!studentRepository.existsById(student.getUniversityNumber())){
            studentRepository.save(student);
        }
        else {
            throw new DuplicateRecordException("Student Record Already Exists!");
        }
        return student;
    }

    @Override
    public Student getStudentById(String univNumber) {
        return studentRepository.findById(univNumber).orElseThrow(() -> new StudentNotFoundException("Student Record Not Found!"));
    }

    @Override
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Student student, String univNumber) {
        if(studentRepository.existsById(univNumber)){
            student.setFirstName(student.getFirstName());
            student.setLastName(student.getLastName());
            student.setDepartmentName(student.getDepartmentName());
            student.setPercentage(student.getPercentage());
            student.setDateOfBirth(student.getDateOfBirth());
            studentRepository.save(student);
        }
        else {
            throw new StudentNotFoundException("Student Record Doesn't Exist!");
        }
        return getStudentById(student.getUniversityNumber());
    }

    @Override
    public String deleteStudent(String univNumber) {
        if(!studentRepository.existsById(univNumber)){
            throw new StudentNotFoundException("Student Record Doesn't Exist!");
        }
        studentRepository.deleteById(univNumber);
        return "Student Record Successfully Deleted";
    }

    @Override
    public List<Student> getStudentsByFirstName(String name) {
        return studentRepository.findByFirstNameLikeIgnoreCase(name+"%");
    }

    @Override
    public List<Student> getStudentByDepartmentName(String depName) {
        return studentRepository.findByDepartmentNameLikeIgnoreCase(depName+"%");
    }

    @Override
    public List<Student> getStudentByPercentage(double percent) {
        return studentRepository.findByPercentageEquals(percent);
    }
}
