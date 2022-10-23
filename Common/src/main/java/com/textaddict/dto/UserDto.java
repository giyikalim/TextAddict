package com.textaddict.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String profession;
    private String site;
    private String nickName;
    private String uuid;
    private boolean isAdmin;
    private boolean isAuthor;
    private boolean isReader;
}
