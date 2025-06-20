package com.project.crud_spring.enuns;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("Ativo"), INACTIVE("Inativo");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
