package com.textaddict.userapi.ui.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
public class UserEditRequest {
    @NotNull(message = "First name can not be null")
    @Size(min = 3, message = "First name can not be less then three characters")
    @Size(max = 30, message = "Firstname can not be longer then thırty characters")
    private String firstName;
    @NotNull(message = "Last name can not be null")
    @Size(min = 2, message = "Last name can not be less then three characters")
    @Size(max = 30, message = "Lastname can not be longer then thırty characters")
    private String lastName;
    @NotNull(message = "Password can not be null")
    @Size(min = 8, message = "Password can not be less then eight characters")
    private String password;
    @Email
    @NotNull(message = "Email can not be null")
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String profession;
    private String site;
}
