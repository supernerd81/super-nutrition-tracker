package de.supernerd.meal.repository;

import de.supernerd.meal.models.DailyMeal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyMealRepository extends MongoRepository<DailyMeal, String> {
    List<DailyMeal> findAllByUserId(String userId);
}
