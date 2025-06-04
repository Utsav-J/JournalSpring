package com.utsavj.journalApp.service;
import com.utsavj.journalApp.entity.JournalEntry;
import com.utsavj.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void deleteEntryById(ObjectId id){
        journalEntryRepo.deleteById(id);
    }


}
