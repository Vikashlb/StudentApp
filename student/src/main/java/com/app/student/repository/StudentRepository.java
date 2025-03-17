package com.app.student.repository;

import com.app.student.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

    List<Student> findByFirstNameLikeIgnoreCase(String firstName);

    List<Student> findByDepartmentNameLikeIgnoreCase(String departmentName);

    List<Student> findByPercentageEquals(double percentage);
}
