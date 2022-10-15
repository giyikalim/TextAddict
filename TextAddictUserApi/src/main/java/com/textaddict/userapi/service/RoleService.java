package com.textaddict.userapi.service;

import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    Role findByRoleName(String role);
}
