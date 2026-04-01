package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.JournalEntry;
import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.JournalEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;



    // ✅ Constructor Injection Only
    public JournalEntryService(JournalEntryRepository journalEntryRepository,
                               UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }


    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {

        try {
            UsersDetails user = userService.findByUserName(username);

            if (user == null) {
                throw new RuntimeException("User not found");
            }

            if (journalEntry.getDate() == null) {
                journalEntry.setDate(LocalDateTime.now());
            }

            JournalEntry saved = journalEntryRepository.save(journalEntry);

            if (user.getJournalEntries() == null) {
                user.setJournalEntries(new ArrayList<>());
            }

            user.getJournalEntries().add(saved);

            userService.saveUser(user);

            return saved;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }


    }


    public List<JournalEntry> getAllEntry() {
        return journalEntryRepository.findAll();
    }

    // ✅ Get By ID
    public Optional<JournalEntry> getByIdEntry(String id) {
        return journalEntryRepository.findById(id);
    }

    // ✅ Delete
    public boolean deleteById(String id, String username) {

        Optional<JournalEntry> entryOpt = journalEntryRepository.findById(id);

        if (entryOpt.isEmpty()) {
            return false;
        }

        journalEntryRepository.deleteById(id);

        UsersDetails user = userService.findByUserName(username);

        if (user != null && user.getJournalEntries() != null) {
            user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            userService.saveUser(user);
        }

        return true;
    }

    // ✅ Update
    public JournalEntry updateById(String id, JournalEntry newEntry, String username) {

        Optional<JournalEntry> existing = journalEntryRepository.findById(id);

        if (existing.isEmpty()) {
            return null;
        }

        JournalEntry entry = existing.get();

        if (newEntry.getTitle() != null)
            entry.setTitle(newEntry.getTitle());

        if (newEntry.getContent() != null)
            entry.setContent(newEntry.getContent());

        entry.setDate(LocalDateTime.now());

        return journalEntryRepository.save(entry);
    }
}