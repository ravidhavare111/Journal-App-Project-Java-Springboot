package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.service.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    userEntryService userService;


    @PostMapping
    public void addUserEntry(@RequestBody UserObject newUser) {
        userService.addNewUserEntry(newUser);
    }
}
