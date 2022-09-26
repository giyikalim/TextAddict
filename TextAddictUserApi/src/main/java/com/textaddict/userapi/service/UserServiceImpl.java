package com.textaddict.userapi.service;

import com.textaddict.userapi.model.User;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User createUser(UserCreateRequest userCreateRequest){
        User user=new ModelMapper().map(userCreateRequest, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userCreateRequest.getPassword()));
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


}
