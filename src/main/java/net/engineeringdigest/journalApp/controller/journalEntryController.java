package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalObject;
import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.service.journalEntryService;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    journalEntryService journalService;

    @Autowired
    userEntryService userService;


    //POST
    @PostMapping("/add/{userName}")
    public ResponseEntity<?> addJournalEntity(@RequestBody JournalObject newJournal, @PathVariable String userName){
          journalService.addJournalEntry(newJournal, userName);

            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET (all entry)
    @GetMapping("/all/{userName}")
    public ResponseEntity<List<JournalObject>> getAllJournalEntriesByUser(@PathVariable String userName) {
        UserObject currentUser = userService.getUserEntry(userName);
         List<JournalObject> responseList = currentUser.getJournalList();
         return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //GET (1 entry)
    @GetMapping("/one/{userName}/{id}")
    public ResponseEntity<JournalObject> getJournalEntry(@PathVariable String userName, @PathVariable ObjectId id) {
        Optional<JournalObject> ansObject = Optional.ofNullable(journalService.getJournalEntry(userName, id));
        if(ansObject.isPresent()){
            return new ResponseEntity<>(ansObject.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //DELETE
    @DeleteMapping("/delete/{userName}/{id}")
    public ResponseEntity<?> deleteJournalEntity(@PathVariable String userName, @PathVariable ObjectId id){
        if(journalService.deleteJournalEntry(userName, id)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJournalEntity(@PathVariable ObjectId id, @RequestBody JournalObject newJournal){
        if(journalService.updateJournalEntry(id, newJournal)){
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
    }
}
