package ru.vkr.vkr.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Course {

    @NotNull
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
