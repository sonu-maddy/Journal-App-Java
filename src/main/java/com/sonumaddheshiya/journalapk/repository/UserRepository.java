package com.sonumaddheshiya.journalapk.repository;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UsersDetails, String> {
    UsersDetails findByUsername(String username);


}
