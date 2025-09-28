package com.gupta.foodUp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gupta.foodUp.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String email;

    private  String password;

    private UserRole role;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favorites = new ArrayList();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)  // once user is deleted all saved addresses should be removed
    private List<Address> addresses = new ArrayList<>();
}
