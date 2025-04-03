package de.supernerd.auth;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface AuthAppUserRepository extends MongoRepository<AuthAppUser, String> {
}
