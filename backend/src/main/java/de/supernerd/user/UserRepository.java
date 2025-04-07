package de.supernerd.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<AppUserDetails, String> {
    Optional<AppUserDetails> findByUserid(String userid);
}
