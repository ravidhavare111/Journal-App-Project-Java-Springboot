package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalObject;
import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.service.journalEntryService;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    journalEntryService journalService;

    @Autowired
    userEntryService userService;


    //POST
    @PostMapping("/add")
    public ResponseEntity<?> addJournalEntity(@RequestBody JournalObject newJournal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
          journalService.addJournalEntry(newJournal, userName);

            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET (all entry)
    @GetMapping("/all")
    public ResponseEntity<List<JournalObject>> getAllJournalEntriesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserObject currentUser = userService.getUserEntry(userName);
         List<JournalObject> responseList = currentUser.getJournalList();
         return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //GET (1 entry)
    @GetMapping("/one/{id}")
    public ResponseEntity<JournalObject> getJournalEntry(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserObject user = userService.findByUserName(userName);
        List<JournalObject> collect = user.getJournalList().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            JournalObject ansObject = journalService.getJournalEntryById(id);
                return new ResponseEntity<>(ansObject, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJournalEntity(@PathVariable ObjectId id, @RequestBody JournalObject newJournal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserObject user = userService.findByUserName(userName);
        List<JournalObject> collect = user.getJournalList().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!collect.isEmpty()){
            if(journalService.updateJournalEntry(id, newJournal)){
                return new ResponseEntity<>(true,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
    }

    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJournalEntity(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        if(journalService.deleteJournalEntry(userName, id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
