package com.sonumaddheshiya.journalapk.controller;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.responseApi.WeatherResponse;
import com.sonumaddheshiya.journalapk.service.UserService;
import com.sonumaddheshiya.journalapk.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final UserService userService;

    public PublicController(UserService userService) {

        this.userService = userService;
    }

    WeatherService  weatherService;



    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UsersDetails user) {

        boolean saved = userService.saveNewUser(user);

        if(saved){
            return ResponseEntity.ok("User created successfully");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User not saved");
        }
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(){

        WeatherResponse weatherResponse = weatherService.getWeather("Delhi");

        String greeting = "";

        if (weatherResponse != null){
            greeting = "Weather feels like "
                    + weatherResponse.getCurrent().getFeelsLike();
        }

        return ResponseEntity.ok(greeting);
    }



}
