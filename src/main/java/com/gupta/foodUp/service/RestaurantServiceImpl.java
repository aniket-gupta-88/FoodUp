package com.gupta.foodUp.service;

import com.gupta.foodUp.dto.RestaurantDto;
import com.gupta.foodUp.model.Address;
import com.gupta.foodUp.model.Restaurant;
import com.gupta.foodUp.model.User;
import com.gupta.foodUp.repository.AddressRepository;
import com.gupta.foodUp.repository.RestaurantRepository;
import com.gupta.foodUp.repository.UserRepository;
import com.gupta.foodUp.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = addressRepository.save(req.getAddress());
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(req.getAddress());
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateReq) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if(restaurant.getCuisineType() != null){
            restaurant.setCuisineType(updateReq.getCuisineType());
        }

        if(restaurant.getDescription() != null){
            restaurant.setDescription(updateReq.getDescription());
        }

        if(restaurant.getName() != null){
            restaurant.setName(updateReq.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
       return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Restaurant not found with id : " + id);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if(restaurant == null){
            throw new Exception("Restaurant not found with owner id : "+ userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        List<RestaurantDto> favorites = user.getFavorites();

        boolean wasRemoved = favorites.removeIf(newdto -> newdto.getId().equals(restaurantId));

        if (!wasRemoved) {
            favorites.add(dto);
        }
        userRepository.save(user);

        return dto;

    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
       Restaurant restaurant = findRestaurantById(id);
       restaurant.setOpen(!restaurant.isOpen());
       return restaurantRepository.save(restaurant);
    }
}
