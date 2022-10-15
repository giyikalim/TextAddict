package com.textaddict.userapi.service;

import com.textaddict.userapi.model.User;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    public User createUser(UserCreateRequest userCreateRequest);

    public User getUserDetailsByEmail(String email);
    public Optional<User> findUserById(UUID id);

    public User saveUser(User user);

    User findByIdForRoleUpdate(UUID id);
}
