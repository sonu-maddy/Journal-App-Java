package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ ONLY FOR REGISTRATION
    public UsersDetails registerUser(UsersDetails user) {

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        UsersDetails existing =
                userRepository.findByUsername(user.getUsername());

        if (existing != null) {
            throw new RuntimeException("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ✅ FOR INTERNAL UPDATE (NO DUPLICATE CHECK)
    public UsersDetails saveUser(UsersDetails user) {
        return userRepository.save(user);
    }

    public void saveNewUser(UsersDetails user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public List<UsersDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public UsersDetails findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UsersDetails> deleteById(String id) {
        userRepository.deleteById(id);
        return null;
    }

    // ✅ UPDATE PASSWORD OR DETAILS
    public UsersDetails updateUser(UsersDetails user) {

        UsersDetails existingUser =
                userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (user.getPassword() != null &&
                !user.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
        }

        return userRepository.save(existingUser);
    }
}