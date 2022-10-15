package com.textaddict.userapi.service;

import com.textaddict.constants.RoleEnum;
import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import com.textaddict.userapi.repository.UserRepository;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleService roleService;
    @Override
    public User createUser(UserCreateRequest userCreateRequest){
        User user=new ModelMapper().map(userCreateRequest, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userCreateRequest.getPassword()));

        Role role=roleService.findByRoleName(RoleEnum.LEARNER.getValue());

        Set<Role> roles=new HashSet<>();
        roles.add(role);

        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User getUserDetailsByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByIdForRoleUpdate(UUID id) {
        return userRepository.findByIdForRoleUpdate(id);
    }
}
