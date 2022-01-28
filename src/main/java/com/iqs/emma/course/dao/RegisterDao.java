package com.iqs.emma.course.dao;

import com.iqs.emma.course.domain.RegisterModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterDao extends JpaRepository<RegisterModel, Long> {
}
