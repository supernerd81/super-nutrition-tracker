package de.supernerd.meal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meal")
public class MealController {

    @GetMapping()
    public String getAllNutrition() {
        return "";
    }
}
