package com.iqs.emma.course.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum {
    ACTIVE("Active"), INACTIVE("Inactive");

    private String name;
}
