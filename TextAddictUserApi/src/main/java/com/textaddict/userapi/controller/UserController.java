package com.textaddict.userapi.controller;

import com.textaddict.dto.UserDto;
import com.textaddict.userapi.UserUtils;
import com.textaddict.userapi.data.ArticleApiClient;
import com.textaddict.userapi.exceptions.UserNotFoundException;
import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import com.textaddict.userapi.service.RoleService;
import com.textaddict.userapi.service.UserService;
import com.textaddict.userapi.ui.model.request.UserCreateRequest;
import com.textaddict.userapi.ui.model.request.ProfileEditRequest;
import com.textaddict.userapi.ui.model.request.UserEditRequest;
import com.textaddict.userapi.ui.model.response.UserDetailResponse;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Getter
@Setter
@RefreshScope
public class UserController {
    Logger logger= LoggerFactory.getLogger(UserController.class);
    @Value("${token.expiration.time}")
    private String tokenExpirationTime;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleApiClient articleApiClient;

    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<UserDetailResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User createdUser = userService.createUser(userCreateRequest);
        UserDetailResponse userCreateResponse = new ModelMapper().map(createdUser, UserDetailResponse.class);
        return new ResponseEntity<>(userCreateResponse, HttpStatus.CREATED);
    }

    @PutMapping("/profile/{id}")
    @PreAuthorize("hasRole('ADMIN') or principal == #id")
    public ResponseEntity<UserDetailResponse> editUserProfile(@Valid @RequestBody ProfileEditRequest userEditRequest, @PathVariable String id) {
        User updatedUser=userService.updateProfile(userEditRequest, id);
        ModelMapper modelMapper=new ModelMapper();
        UserDetailResponse userDetailResponse=modelMapper.map(updatedUser, UserDetailResponse.class);
        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDetailResponse> editUser(@Valid @RequestBody UserEditRequest userEditRequest, @PathVariable String id) {
        User updatedUser=userService.updateUser(userEditRequest, id);
        ModelMapper modelMapper=new ModelMapper();
        UserDetailResponse userDetailResponse=modelMapper.map(updatedUser, UserDetailResponse.class);
        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/role/{role}")
    public ResponseEntity<String> changeUserRole(@PathVariable String id, @PathVariable String role){
        Role roleFound=roleService.findByRoleName(role);
        if(roleFound==null){
            return ResponseEntity.badRequest().body("Role could not found");
        }

        User userFound=userService.findByIdForRoleUpdate(UUID.fromString(id));

        if(userFound==null){
            return ResponseEntity.badRequest().body("User could not found");
        }

        Set<Role> roleList=userFound.getRoles()==null?new HashSet<>():userFound.getRoles();
        roleList.add(roleFound);
        userFound.setRoles(roleList);

        userService.saveUser(userFound);

        return ResponseEntity.ok("User role updated");
    }

    @GetMapping("/exception")
    public String getException() {
        return "basarili tokenExpirationTime is: " + tokenExpirationTime;
        //if(true) throw new UserNotPermittedException("Special exception");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<User> users=userService.getAllUsers();
        ModelMapper modelMapper=new ModelMapper();
        List<UserDto> usersDto=users.stream().map(u->{
            UserDto userDto=modelMapper.map(u, UserDto.class);
            UserUtils.setAuthorities(userDto, u.getRoles());
            userDto.setUuid(u.getId().toString());
            return userDto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') or principal == #id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetail(@PathVariable("id") String id) {
        Optional<User> user = userService.findUserById(UUID.fromString(id));
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with given identifier");
        }

        //logger.info("Before calling article api");
        //List<ArticleResponse> articles=articleApiClient.getArticles("1");
        //logger.info("After calling article api");
        UserDto userdetailed=new ModelMapper().map(user.get(), UserDto.class);
        userdetailed.setUuid(user.get().getId().toString());
        return new ResponseEntity<>(userdetailed, HttpStatus.OK);
    }


}
