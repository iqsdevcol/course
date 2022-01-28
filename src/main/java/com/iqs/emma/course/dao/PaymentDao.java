package com.iqs.emma.course.dao;

import com.iqs.emma.course.domain.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDao extends JpaRepository<PaymentModel, Long> {
}
