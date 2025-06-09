package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document()
@Data
public class UserObject {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NotNull
    private String userName;

    @NotNull
    private String password;

    @DBRef
    private List<JournalObject> journalList = new ArrayList<>();

    private List<String> roles;
}
