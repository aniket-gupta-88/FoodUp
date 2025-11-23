package com.gupta.foodUp.repository;

import com.gupta.foodUp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
