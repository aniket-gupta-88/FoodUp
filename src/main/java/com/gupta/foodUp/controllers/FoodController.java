package com.gupta.foodUp.controllers;

import com.gupta.foodUp.model.Food;
import com.gupta.foodUp.model.Restaurant;
import com.gupta.foodUp.model.User;
import com.gupta.foodUp.request.CreateFoodRequest;
import com.gupta.foodUp.service.FoodService;
import com.gupta.foodUp.service.RestaurantService;
import com.gupta.foodUp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping()
    public ResponseEntity<List<Food>> searchFood(
            @RequestParam String keyword,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(keyword);
        return new ResponseEntity<>(foods, HttpStatus.OK);

    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @PathVariable Long id,
            @RequestParam boolean vegetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @RequestParam(required = false) String category,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(id, vegetarian,nonveg,seasonal, category);

        return new ResponseEntity<>(foods, HttpStatus.OK);

    }



}
