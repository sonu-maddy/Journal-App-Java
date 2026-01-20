package com.sonumaddheshiya.journalapk.controller;

import com.sonumaddheshiya.journalapk.entity.JournalEntry;
import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import com.sonumaddheshiya.journalapk.service.JournalEntryService;
import com.sonumaddheshiya.journalapk.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DuplicateFormatFlagsException;
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

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody UsersDetails user) {
        try {
            return ResponseEntity.ok(userService.saveUser(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PutMapping("update-user")
    public ResponseEntity<?> updateUser(@RequestBody UsersDetails user){
        UsersDetails usersDetails = userService.updateUser(user);
        return new ResponseEntity<>(usersDetails, HttpStatus.OK);
    }



}
