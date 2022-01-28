package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "payment")
@Data
@NoArgsConstructor
public class PaymentModel {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "register_id")
    private RegisterModel register;
    private LocalDateTime creationDate;
    private LocalDate paymentDate;
    private double value;
    private String url;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public PaymentModel(RegisterModel register, LocalDateTime creationDate, LocalDate paymentDate, double value, StatusEnum status) {
        this.register = register;
        this.creationDate = creationDate;
        this.paymentDate = paymentDate;
        this.value = value;
        this.status = status;
    }
}
