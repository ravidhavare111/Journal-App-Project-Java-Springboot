package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

//POJO class
@Document
@Data
public class JournalObject {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

}
