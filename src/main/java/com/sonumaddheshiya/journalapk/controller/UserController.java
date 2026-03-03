package com.sonumaddheshiya.journalapk.controller;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }


    @GetMapping ("all-user")
    public List<UsersDetails> getAllUser(){

        return userService.getAllUsers();
    }

    @GetMapping ("userId/{username}")
    public UsersDetails getUserById(@PathVariable String username){

        return userService.findByUserName(username);
    }

    @DeleteMapping("delete-user/{id}")
    public Optional<UsersDetails> deleteUser(@PathVariable String id){

        return userService.deleteById(id);
    }



    @PutMapping("update-user")
    public ResponseEntity<?> updateUser(@RequestBody UsersDetails user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        UsersDetails usersDetails = userService.updateUser(user);
        return new ResponseEntity<>(usersDetails, HttpStatus.OK);
    }



}
