package de.supernerd.meal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/meal")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) { this.mealService = mealService; }

//    @GetMapping("")
//    public String getAllMeal() {
//        return "";
//    }
//
//    @PostMapping
//    public String addMeal() {
//        return "";
//    }
}
