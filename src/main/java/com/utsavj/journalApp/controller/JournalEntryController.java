package com.utsavj.journalApp.controller;
import com.utsavj.journalApp.entity.JournalEntry;
import com.utsavj.journalApp.entity.User;
import com.utsavj.journalApp.service.JournalEntryService;
import com.utsavj.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//controller -> service -> repository

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.getUserByUsername(username);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username){
        try {
            journalEntryService.saveEntry(journalEntry, username);
            return new ResponseEntity<JournalEntry>(journalEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry  = journalEntryService.getEntryById(myId);
        if (journalEntry.isPresent()){
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> removeEntryById(@PathVariable String username,@PathVariable ObjectId myId){
        journalEntryService.deleteEntryById(username,myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/id/{username}/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable String username,
                                                        @PathVariable ObjectId myId,
                                                        @RequestBody JournalEntry journalEntry)
    {
        try{
            JournalEntry targetEntry = journalEntryService.getEntryById(myId).orElse(null);
            if (targetEntry != null){
                targetEntry.setTitle(journalEntry.getTitle() != null  && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : targetEntry.getTitle());
                targetEntry.setContent(journalEntry.getContent() != null &&  !journalEntry.getContent().equals("") ? journalEntry.getContent() : targetEntry.getContent());
            }
            journalEntryService.saveEntry(targetEntry);
            return new ResponseEntity<JournalEntry>(targetEntry, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

