package com.project.crud_spring.enuns;

import lombok.Getter;

@Getter
public enum Category {
    BACKEND("Back-end"), FRONTEND("Front-end");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
