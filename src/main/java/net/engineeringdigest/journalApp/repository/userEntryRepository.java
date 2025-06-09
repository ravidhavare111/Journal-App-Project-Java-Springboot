package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.UserObject;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userEntryRepository extends MongoRepository<UserObject, ObjectId> {
    UserObject findByUserName(String userName);

    void deleteByUserName(String userName);
}
