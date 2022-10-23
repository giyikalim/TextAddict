package com.textaddict.userapi;

import com.textaddict.dto.UserDto;
import com.textaddict.userapi.model.Role;

import java.util.List;
import java.util.Set;

public class UserUtils {
    public static void setAuthorities(UserDto userDto, Set<Role> roles){
        userDto.setAdmin(roles.stream().anyMatch(r->r.getRole().equals("admin")));
        userDto.setAuthor(roles.stream().anyMatch(r->r.getRole().equals("author")));
        userDto.setReader(roles.stream().anyMatch(r->r.getRole().equals("reader")));
    }
}
