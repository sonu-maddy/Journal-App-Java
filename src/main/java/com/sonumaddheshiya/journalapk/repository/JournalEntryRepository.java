package com.sonumaddheshiya.journalapk.repository;

import com.sonumaddheshiya.journalapk.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}
