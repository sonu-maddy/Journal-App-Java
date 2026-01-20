package com.sonumaddheshiya.journalapk;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoDebug {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @PostConstruct
    public void logUri() {
        System.out.println("MONGO URI = " + uri);
    }
}

