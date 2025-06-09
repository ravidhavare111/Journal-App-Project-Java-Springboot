package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.repository.userEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Encoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class userEntryService {

    @Autowired
    userEntryRepository EntryUserRepository;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void addUserEntry(UserObject newUser) {
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.setRoles(Arrays.asList("Admin"));
        EntryUserRepository.save(newUser);
    }

    public List<UserObject> getAllUserEntries() {
        return EntryUserRepository.findAll();
    }

    public UserObject getUserEntry(String UserName){
        return EntryUserRepository.findByUserName(UserName);
    }

    public boolean updateUserEntry(UserObject newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserObject oldUser = EntryUserRepository.findByUserName(userName);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        oldUser.setUserName(newUser.getUserName());
        oldUser.setPassword(newUser.getPassword());
        EntryUserRepository.save(oldUser);
        return true;
    }

    public boolean deleteUserEntry() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        EntryUserRepository.deleteByUserName(userName);
        return true;
    }
}
