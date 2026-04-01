package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.responseApi.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private static final  String apiKey = "6ecae1a0bd3bed0e84645b49a1e2c178";

    private static final String API =
            "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    public WeatherResponse getWeather(String city){
        String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse weatherResponse = responseEntity.getBody();
        return weatherResponse;
    }
}
