package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalObject;
import net.engineeringdigest.journalApp.entity.UserObject;
import net.engineeringdigest.journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class journalEntryService {

    @Autowired
    journalEntryRepository EntryRepository;

    @Autowired
    userEntryService userEntryService;


    @Transactional
    public void addJournalEntry(JournalObject newJournal, String userName) {
        try {
            newJournal.setDate(LocalDateTime.now());
            JournalObject journal = EntryRepository.save(newJournal);
            UserObject user = userEntryService.getUserEntry(userName);
            user.getJournalList().add(journal);
//            user.setUserName(null);
            userEntryService.addUserEntry(user);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public List<JournalObject> getAllJournalEntries() {
        return EntryRepository.findAll();
    }

    public JournalObject getJournalEntry(String userName, ObjectId id) {

        UserObject user = userEntryService.getUserEntry(userName);
        List<JournalObject> temp = user.getJournalList();
        for(JournalObject J : temp){
            if(J.getId().equals(id)){
                return J;
            }
        }
        return null;
//        return EntryRepository.findById(id).orElse(null);
    }

    public boolean deleteJournalEntry(String userName, ObjectId id) {

        JournalObject tempObject = getJournalEntry(userName, id);
        if(tempObject != null){
            EntryRepository.deleteById(id);
            UserObject user = userEntryService.getUserEntry(userName);
            user.getJournalList().removeIf(journal -> journal.getId().equals(id));
            userEntryService.addUserEntry(user);
            return true;
        }

        return false;
    }

    public boolean updateJournalEntry(ObjectId id, JournalObject newJournal) {
        JournalObject oldJournal = EntryRepository.findById(id).orElse(null);
        if(oldJournal != null){
            oldJournal.setTitle(newJournal.getTitle() != null && newJournal.getTitle() != "" ? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(newJournal.getContent() != null && newJournal.getContent() != "" ? newJournal.getContent() : oldJournal.getContent());

            EntryRepository.save(oldJournal);
            return true;
        }

//        if(oldJournal != null){
//            if( newJournal.getTitle() != null && newJournal.getTitle() != "" && !oldJournal.getTitle().equals(newJournal.getTitle())){
//                oldJournal.setTitle(newJournal.getTitle());
//            }
//            if(newJournal.getContent() != null && newJournal.getContent() != "" && !oldJournal.getContent().equals(newJournal.getContent())){
//                oldJournal.setContent(newJournal.getContent());
//            }
//        }

        return false;

    }






}
