package com.sonumaddheshiya.journalapk.controller;

import com.sonumaddheshiya.journalapk.entity.JournalEntry;
import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.JournalEntryRepository;
import com.sonumaddheshiya.journalapk.service.JournalEntryService;
import com.sonumaddheshiya.journalapk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    private final JournalEntryService journalEntryService;
    private final UserService userService;
    private final JournalEntryRepository journalEntryRepository;

    // Constructor Injection
    public JournalEntryControllerV2(
            JournalEntryService journalEntryService,
            UserService userService, JournalEntryRepository journalEntryRepository) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
        this.journalEntryRepository = journalEntryRepository;
    }

    // ✅ Get All Entries (Logged-in User Only)
    @GetMapping("/all")
    public ResponseEntity<?> getAllJournalEntryByUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        UsersDetails user = userService.findByUserName(username);

        if (user == null || user.getJournalEntries() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.getJournalEntries());
    }

    // ✅ Create Entry
    @PostMapping("/create")
    public ResponseEntity<?> createEntry(
            @RequestBody JournalEntry myEntry) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();



        journalEntryService.saveEntry(myEntry, username);
        return ResponseEntity.status(201).body(myEntry);
    }

    // ✅ Get By ID (Only If Belongs To Logged-in User)
    @GetMapping("id/{myid}")
    public ResponseEntity<?> getJournalEntryById(
            @PathVariable String myid) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        UsersDetails user = userService.findByUserName(username);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());

        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(myid);

            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // ✅ Delete Entry (Secure)
    @Transactional
    @DeleteMapping("delete/{id}")
    public boolean deleteById(String id, String username) {

        UsersDetails user = userService.findByUserName(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Optional<JournalEntry> entryOpt =
                journalEntryRepository.findById(id);

        if (entryOpt.isEmpty()) {
            return false;
        }

        // 1️⃣ Remove from journal collection
        journalEntryRepository.deleteById(id);

        // 2️⃣ Remove from user list
        if (user.getJournalEntries() != null) {
            user.getJournalEntries()
                    .removeIf(entry -> entry.getId().equals(id));
        }

        userService.saveUser(user);

        return true;
    }

    // ✅ Update Entry (Secure)
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable String id,
            @RequestBody JournalEntry newEntry) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        UsersDetails user = userService.findByUserName(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

        if (collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getByIdEntry(id);
            if (journalEntry.isPresent()){
                JournalEntry oldEntry = journalEntry.get();
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals(" ") ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry, username);
                return new  ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }

       return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}