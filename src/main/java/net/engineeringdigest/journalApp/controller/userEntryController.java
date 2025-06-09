package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class userEntryController {

    @Autowired
    userEntryService userService;



    @GetMapping("/all")
    public List<UserObject> getAllUserEntries() {
        return userService.getAllUserEntries();
    }

    @GetMapping("/one/{UserName}")
    public UserObject getuserEntry(@PathVariable String UserName) {
        return userService.getUserEntry(UserName);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserEntry(@RequestBody UserObject newUser) {
        if(userService.updateUserEntry(newUser)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserEntry() {
        if(userService.deleteUserEntry()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
