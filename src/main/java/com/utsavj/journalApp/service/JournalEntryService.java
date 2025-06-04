package com.utsavj.journalApp.service;
import com.utsavj.journalApp.entity.JournalEntry;
import com.utsavj.journalApp.entity.User;
import com.utsavj.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        User user = userService.getUserByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
//        user.setUsername(null); // uncomment this if you wanna test the transactional nature of this method
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void deleteEntryById(String username, ObjectId id){
        User user = userService.getUserByUsername(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepo.deleteById(id);
    }


}
