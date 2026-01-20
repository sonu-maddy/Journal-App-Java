package com.sonumaddheshiya.journalapk.controller;

import com.sonumaddheshiya.journalapk.entity.JournalEntry;
import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import com.sonumaddheshiya.journalapk.service.JournalEntryService;
import com.sonumaddheshiya.journalapk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    private static final Logger log = LoggerFactory.getLogger(JournalEntryControllerV2.class);
    @Autowired
    private final JournalEntryService journalEntryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    public JournalEntryControllerV2(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @GetMapping("all-entry/{username}")
    public ResponseEntity<?> getAllJournalEntryByUser(@PathVariable String username){
        UsersDetails user = userRepository.findByUsername(username);

          List<JournalEntry> all =  user.getJournalEntries();

        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("create-entry/{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String username){

        try {
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-id/{myId}")
    public Optional<JournalEntry> getJournalEntryById(@PathVariable String myId){
        return journalEntryService.getByIdEntry(myId);
    }

    @DeleteMapping("/delete/{username}/{myId}")
    public Optional<JournalEntry> deleteJournalEntryById(
            @PathVariable String myId,
            @PathVariable String username) {

        UsersDetails usersDetails = userService.findByUserName(username);

        if (usersDetails.getJournalEntries() != null) {
            usersDetails.getJournalEntries().removeIf(x -> x.getId().equals(myId));
            userService.updateUser(usersDetails);
        }

        return journalEntryService.deleteById(myId, username);
    }


    @PutMapping("update/{username}/{id}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable String id,
            @RequestBody JournalEntry newEntry,
            @PathVariable String username) {

        JournalEntry old = journalEntryService.getByIdEntry(id).orElse(null);

        if (old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new  ResponseEntity<>(HttpStatus.OK);
        }

        return new  ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}
