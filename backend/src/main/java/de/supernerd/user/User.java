package de.supernerd.user;

import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("user")
@With
public record User(
     @id
     String id,
     String firstname,
     String lastname,
     LocalDate birthday,
     int weight,
     int height
) {
}
