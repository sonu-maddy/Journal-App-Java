package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public UsersDetails saveUser(UsersDetails user) {

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        Optional<UsersDetails> existingUser =
                Optional.ofNullable(userRepository.findByUsername(user.getUsername()));

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }



    public List<UsersDetails> getAllUsers() {

        return userRepository.findAll();
    }

    public UsersDetails findByUserName(String username) {
        return userRepository.findByUsername(username);
    }



    public Optional<UsersDetails> deleteById(String id) {
        Optional<UsersDetails> user = userRepository.findById(id);
        user.ifPresent(u -> userRepository.deleteById(id));
        return user;
    }

    public UsersDetails updateUser(UsersDetails user) {

        UsersDetails existingUser =
                userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        // update fields
        existingUser.setPassword(user.getPassword());
        existingUser.setUsername(user.getUsername());

        return userRepository.save(existingUser);
    }

}
