package com.gupta.foodUp.service;

import com.gupta.foodUp.model.Category;
import com.gupta.foodUp.model.Food;
import com.gupta.foodUp.model.Restaurant;
import com.gupta.foodUp.repository.FoodRepository;
import com.gupta.foodUp.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredientsItems(req.getIngredients());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());

        Food savedFood =  foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
//        foodRepository.deleteById(foodId);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarian, boolean isNonveg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);


        foods = foods.stream()

                .filter(food -> {
                    if (isVegetarian && food.isVegetarian()) {
                        return true;
                    }
                    if (isNonveg && !food.isVegetarian()) {
                        return true;
                    }

                    if (!isVegetarian && !isNonveg) {
                        return true;
                    }
                    return false;
                })

                .filter(food -> {
                    if (isSeasonal && food.isSeasonal()) {
                        return true;
                    }
                    if (!isSeasonal) {
                        return true;
                    }
                    return false;
                })
                .filter(food -> {

                    if (foodCategory != null && !foodCategory.isBlank()) {
                        return food.getFoodCategory().getName().equalsIgnoreCase(foodCategory);
                    }
                    return true;
                })

                .collect(Collectors.toList());

        return foods;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);

        if(optionalFood.isEmpty()){
            throw new Exception("Food not exits...");
        }

        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);

    }
}
