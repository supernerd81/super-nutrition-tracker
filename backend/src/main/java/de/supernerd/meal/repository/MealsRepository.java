package de.supernerd.meal.repository;

import de.supernerd.meal.models.Meals;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealsRepository extends MongoRepository<Meals, String> {
    Optional<Meals> findByName(String mealName);
    Optional<Meals> findByBarcode(String barcode);
}
