package com.iqs.emma.course.dao;

import com.iqs.emma.course.domain.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentDao extends JpaRepository<StudentModel,Long> {
    Optional<StudentModel> findByDocument(Long document);

    Optional<StudentModel> findByEmail(String email);
}
