package com.app.student.controller;

import com.app.student.entity.Student;
import com.app.student.service.StudentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeacherController {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @PostMapping("/addStudenta")
    public Student addStudent(   @RequestBody @Valid Student student) {

        return studentServiceImpl.addStudent(student);
    }

    @GetMapping("/getStudentByIda/{id}")
    public Student getStudentById(     @PathVariable("id") String univNumber) {
        return studentServiceImpl.getStudentById(univNumber);
    }

    @GetMapping("/getAllStudentsa")
    public List<Student> getAllStudents() {

          return studentServiceImpl.getAllStudents();
    }

    @PutMapping("/updateStudenta/{id}")
    public Student updateStudent(@RequestBody @Valid Student student, @PathVariable("id") String univNumber) {
               return studentServiceImpl.updateStudent(student, univNumber);
    }

    @DeleteMapping("/deleteStudentByIda/{id}")
    public String deleteStudentById(@PathVariable("id") String univNumber) {
                 return studentServiceImpl.deleteStudent(univNumber);
    }

    @GetMapping("/getStudentByNamea/{name}")
    public List<Student> getStudentByName(@PathVariable("name") String firstName) {
                 return studentServiceImpl.getStudentsByFirstName(firstName);
    }

    @GetMapping("/getStudentByDepartmenta/{department}")
    public List<Student> getStudentByDepartment(@PathVariable("department") String departmentName)
    {
           return studentServiceImpl.getStudentByDepartmentName(departmentName);
    }

    @GetMapping("/getStudentByPercentagea/{percentage}")
    public List<Student> getStudentByPercentage(@PathVariable("percentage") double percentage)
    {
        return studentServiceImpl.getStudentByPercentage(percentage);
    }
}
