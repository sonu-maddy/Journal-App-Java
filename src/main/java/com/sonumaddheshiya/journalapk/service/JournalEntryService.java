package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.JournalEntry;
import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import com.sonumaddheshiya.journalapk.repository.JournalEntryRepository;
import com.sonumaddheshiya.journalapk.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {


    // private static final Logger log = (Logger) LoggerFactory.getLogger(JournalEntryService.class);

    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;

    @Autowired
    UserService userService;
    private Process log;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserRepository userRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {

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
        userRepository.save(user);

        return saved;
    }


    public JournalEntry saveEntry(JournalEntry journalEntry) {

        return journalEntryRepository.save(journalEntry);
    }



    public List<JournalEntry> getAllEntry() {

        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getByIdEntry(String id){

        return journalEntryRepository.findById(id);
    }

    public Optional<JournalEntry> deleteById(String id, String username) {

        Optional<JournalEntry> entryOpt = journalEntryRepository.findById(id);

        if (entryOpt.isEmpty()) {
            return Optional.empty();
        }

        journalEntryRepository.deleteById(id);

        return entryOpt;
    }


    public Optional<JournalEntry> updateById(String id, JournalEntry newEntry){
        Optional<JournalEntry> existing = journalEntryRepository.findById(id);

        if (existing.isPresent()) {
            JournalEntry entry = existing.get();
            entry.setTitle(newEntry.getTitle());
            entry.setContent(newEntry.getContent());
            entry.setDate(LocalDateTime.now());
            journalEntryRepository.save(entry);
        }

        return existing;
    }
}
