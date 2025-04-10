package de.supernerd.meal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealCatalogRepository extends MongoRepository<MealCatalog, String> {
}
