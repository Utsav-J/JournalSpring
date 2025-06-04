package com.utsavj.journalApp.controller;
import com.utsavj.journalApp.entity.JournalEntry;
import com.utsavj.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//controller -> service -> repository

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAllEntries();
    }


    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getEntryById(myId).orElse(null);
    }

    @DeleteMapping("/id/{myId}")
    public boolean removeEntryById(@PathVariable ObjectId myId){
        journalEntryService.deleteEntryById(myId);
        return true;
    }


    @PutMapping("/id/{myId}")
    public JournalEntry updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry journalEntry){
        JournalEntry targetEntry = journalEntryService.getEntryById(myId).orElse(null);
        if (targetEntry != null){
            targetEntry.setTitle(journalEntry.getTitle() != null  && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : targetEntry.getTitle());
            targetEntry.setContent(journalEntry.getContent() != null &&  !journalEntry.getContent().equals("") ? journalEntry.getContent() : targetEntry.getContent());
        }
        journalEntryService.saveEntry(targetEntry);
        return targetEntry;
    }
}

