package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalObject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface journalEntryRepository extends MongoRepository<JournalObject, ObjectId> {

}
