package com.textaddict.article.query.ui.model.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserRequest {
    @NotNull(message = "Name can not be empty")
    private String name;
    @Max(value = 60, message = "age can not be more than 60")
    @Min(value = 18, message = "age can not be less than 18")
    private Integer age;
}
