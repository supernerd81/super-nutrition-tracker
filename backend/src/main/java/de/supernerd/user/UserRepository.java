package de.supernerd.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<AppUserUpdate, String> {
    Optional<AppUserUpdate> findByUserid(String userid);
}
