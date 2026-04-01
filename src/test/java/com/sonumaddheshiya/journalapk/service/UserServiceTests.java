package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  UserService userService;

    @Test
    public void testAdd(){
        assertEquals(4, 2+2);
        assertTrue(5>4);
    }

    @BeforeEach
    public void test2(){ }

    @BeforeAll
    public static void test3(){ }

    @AfterAll
    public static void test4(){}

   @Test
   public void findUser(){
        assertNotNull(userRepository.findByUsername("sonu"));

        UsersDetails user = userRepository.findByUsername("sonu");

        assertEquals("sonu",user.getUsername(), userRepository.findByUsername("sonu").getUsername());

        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "sonu",
            "vinit",
            "akash"
    })
    public void findUser2(String name){
        assertNotNull(userRepository.findByUsername(name));

    }

   @ParameterizedTest
   @ArgumentsSource(UserAgumentProvider.class)
    public void SaveNewUser(UsersDetails user){
       assertTrue(userService.saveNewUser(user));

    }


    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "4,2,6",
            "2,4,6"
    })
    public void test(int a, int b, int expected){

        assertEquals(expected, a+b);
    }
}
