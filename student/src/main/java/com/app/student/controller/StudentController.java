package com.app.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.student.entity.Student;
import com.app.student.service.StudentServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student) {
       return studentServiceImpl.addStudent(student);
    }

    @GetMapping("/getStudentById/{id}")
    public Student getStudentById(@PathVariable("id") String univNumber) {
        return studentServiceImpl.getStudentById(univNumber);
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() {
        return studentServiceImpl.getAllStudents();
    }

    @PutMapping("/updateStudent/{id}")
    public Student updateStudent(@RequestBody Student student, @PathVariable("id") String univNumber) {
        return studentServiceImpl.updateStudent(student,univNumber);
    }

    @DeleteMapping("/deleteStudentById/{id}")
    public String deleteStudentById(@PathVariable("id") String univNumber) {
        return studentServiceImpl.deleteStudent(univNumber);
    }

    @GetMapping("/getStudentByName/{name}")
    public List<Student> getStudentByName(@PathVariable("name")String firstName) {
        return studentServiceImpl.getStudentsByFirstName(firstName);
    }

    @GetMapping("/getStudentByDepartment/{department}")
    public List<Student> getStudentByDepartment(@PathVariable("department") String departmentName) {
        return studentServiceImpl.getStudentByDepartmentName(departmentName);
    }

    @GetMapping("/getStudentByPercentage/{percentage}")
    public List<Student> getStudentByPercentage(@PathVariable("percentage") double percentage) {
        return studentServiceImpl.getStudentByPercentage(percentage);
    }
}
