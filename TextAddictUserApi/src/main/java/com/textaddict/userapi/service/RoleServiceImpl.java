package com.textaddict.userapi.service;

import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import com.textaddict.userapi.repository.RoleRepository;
import com.textaddict.userapi.repository.UserRepository;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }
}
