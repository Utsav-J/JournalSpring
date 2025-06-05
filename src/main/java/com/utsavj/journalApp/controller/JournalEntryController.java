package com.utsavj.journalApp.controller;
import com.utsavj.journalApp.entity.JournalEntry;
import com.utsavj.journalApp.entity.User;
import com.utsavj.journalApp.service.JournalEntryService;
import com.utsavj.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//controller -> service -> repository

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if (allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(journalEntry, username);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);

        List<JournalEntry> collect = currentUser.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if ( !collect.isEmpty() ){
            Optional<JournalEntry> journalEntry  = journalEntryService.getEntryById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> removeEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deleteEntryById(username,myId);
        if (removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId,
                                                        @RequestBody JournalEntry journalEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);
        List<JournalEntry> collect = currentUser.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();

        if(!collect.isEmpty()){
            Optional<JournalEntry> old  = journalEntryService.getEntryById(myId);
            if (old.isPresent()){
                JournalEntry targetEntry = old.get();
                targetEntry.setTitle(journalEntry.getTitle() != null  && !journalEntry.getTitle().equals("") ? journalEntry.getTitle() : targetEntry.getTitle());
                targetEntry.setContent(journalEntry.getContent() != null &&  !journalEntry.getContent().equals("") ? journalEntry.getContent() : targetEntry.getContent());
                journalEntryService.saveEntry(targetEntry);
                return new ResponseEntity<JournalEntry>(targetEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}



