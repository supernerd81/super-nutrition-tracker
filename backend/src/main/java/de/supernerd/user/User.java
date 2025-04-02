package de.supernerd.user;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("user")
@With
public record User(
     @Id
     String id,
     String firstname,
     String lastname,
     LocalDate birthday,
     int weight,
     int height
) {
}
