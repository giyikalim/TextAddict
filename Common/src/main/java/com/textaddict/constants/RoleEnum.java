package com.textaddict.constants;

public enum RoleEnum {
    LEARNER("learner"), AUTHOR("author");

    private final String value;

    public String getValue(){
        return value;
    }

    RoleEnum(String value) {
        this.value = value;
    }
}
