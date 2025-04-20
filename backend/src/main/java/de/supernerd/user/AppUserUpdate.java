package de.supernerd.user;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("authAppUser")
@With
@Builder
public record AppUserUpdate(
     @Id
     String id,
     String userid,
     String firstname,
     String lastname,
     LocalDate birthday,
     double weight,
     double height,
     Gender gender
) {
}
