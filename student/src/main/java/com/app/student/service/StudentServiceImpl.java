package com.app.student.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.student.entity.Student;
import com.app.student.exception.DataUnavailableException;
import com.app.student.exception.DuplicateDataException;
import com.app.student.exception.InvalidDataException;
import com.app.student.repository.StudentRepository;
import org.springframework.util.StringUtils;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    final String pattern = "^[a-zA-Z]+$";
    @Override
    public Student addStudent(Student student) {
        /* if(!student.getUniversityNumber().isEmpty() && student.getFirstName().matches(pattern) &&
                student.getLastName().matches(pattern) && !student.getDepartmentName().isEmpty() &&
                student.getPercentage() != 0.00 && student.getDateOfBirth() != null) { */
        if (StringUtils.hasText(student.getUniversityNumber()) &&
                StringUtils.hasText(student.getFirstName()) &&
                student.getFirstName().matches(pattern) &&
                StringUtils.hasText(student.getLastName()) &&
                student.getLastName().matches(pattern) &&
                StringUtils.hasText(student.getDepartmentName()) &&
                student.getPercentage() != 0.00 &&
                Objects.nonNull(student.getDateOfBirth())) {
            if(!studentRepository.existsById(student.getUniversityNumber())){ //if(!checkIfStudentExists(student.getUniversityNumber())){
                studentRepository.save(student);
            }
            else {
                throw new DuplicateDataException("Student Record Already Exists!");
            }
        }
        else {
            throw new InvalidDataException("Invalid Data!");
        }
        return student;
    }


    /*private Student mapToObject(Student student) {
        return new Student(student.getUniversityNumber(),student.getFirstName(),student.getLastName(),
                student.getDepartmentName(),student.getPercentage(), student.getDateOfBirth());
    }
*/
    @Override
    public Student getStudentById(String univNumber) {
        //Optional<Student> data =  studentRepository.findById(univNumber);
        return studentRepository.findById(univNumber).orElseThrow(() -> new DataUnavailableException("Student Record Not Found!"));
        //return data.map(this::mapToObject).orElseThrow(() -> new DataUnavailableException("Data unavailable!"));
    }

    @Override
    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

/*    private boolean checkIfStudentExists(String univNumber) {
        Optional<Student> student =  studentRepository.findById(univNumber);
        return student.isPresent();
                //.orElseThrow(() -> new DataUnavailableException("Student Record Not Found!"));
    }*/

    @Override
    public Student updateStudent(Student student, String univNumber) {
        /*if(!student.getUniversityNumber().isEmpty() && student.getFirstName().matches(pattern) &&
                student.getLastName().matches(pattern) && !student.getDepartmentName().isEmpty() &&
                student.getPercentage() != 0.00 && student.getDateOfBirth() != null) { */
        if (StringUtils.hasText(student.getUniversityNumber()) &&
                    StringUtils.hasText(student.getFirstName()) &&
                    student.getFirstName().matches(pattern) &&
                    StringUtils.hasText(student.getLastName()) &&
                    student.getLastName().matches(pattern) &&
                    StringUtils.hasText(student.getDepartmentName()) &&
                    student.getPercentage() != 0.00 &&
                    Objects.nonNull(student.getDateOfBirth())) {
            if(studentRepository.existsById(univNumber)){ //if(checkIfStudentExists(univNumber)){
                student.setFirstName(student.getFirstName());
                student.setLastName(student.getLastName());
                student.setDepartmentName(student.getDepartmentName());
                student.setPercentage(student.getPercentage());
                student.setDateOfBirth(student.getDateOfBirth());
                studentRepository.save(student);
            }
            else {
                throw new DataUnavailableException("Student Record Doesn't Exist!");
            }
        }
        else {
            throw new InvalidDataException("Invalid Data!");
        }
        return getStudentById(student.getUniversityNumber());
    }

    @Override
    public String deleteStudent(String univNumber) {
        if(!studentRepository.existsById(univNumber)){ //if(!checkIfStudentExists(univNumber)){
            throw new DataUnavailableException("Student Record Doesn't Exist!");
        }
        studentRepository.deleteById(univNumber);
        return "Record Successfully Deleted";
    }

    @Override
    public List<Student> getStudentsByFirstName(String name) {  //, String department, double percentage) {
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
