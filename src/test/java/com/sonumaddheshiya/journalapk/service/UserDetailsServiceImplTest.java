package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsernametest(){

        UsersDetails mockUser = UsersDetails.builder()
                .username("sonu")
                .password("htcfgvhb")
                .roles(new ArrayList<>())
                .build();

        when(userRepository.findByUsername(anyString()))
                .thenReturn(mockUser);

        UsersDetails user = (UsersDetails) userDetailsService.loadUserByUsername("sonu");

        Assertions.assertNotNull(user);
    }
}