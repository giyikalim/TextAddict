package com.textaddict.userapi.service;

import com.textaddict.constants.RoleEnum;
import com.textaddict.userapi.exceptions.UserNotFoundException;
import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import com.textaddict.userapi.repository.UserRepository;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import com.textaddict.userapi.ui.model.request.ProfileEditRequest;
import com.textaddict.userapi.ui.model.request.UserEditRequest;
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
    public User updateUser(UserEditRequest userEditRequest, String userId){
        Optional<User> userOptional=userRepository.findById(UUID.fromString(userId));
        User user=userOptional.orElseThrow(()->new UserNotFoundException("User could not found"));

        user.setFirstName(userEditRequest.getFirstName());
        user.setLastName(userEditRequest.getLastName());
        user.setEmail(userEditRequest.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(ProfileEditRequest userEditRequest, String userId){
        Optional<User> userOptional=userRepository.findById(UUID.fromString(userId));
        User user=userOptional.orElseThrow(()->new UserNotFoundException("User could not found"));

        user.setFirstName(userEditRequest.getFirstName());
        user.setLastName(userEditRequest.getLastName());
        user.setEmail(userEditRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userEditRequest.getPassword()));
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

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAllWithRole();
    }
}
