package com.gupta.foodUp.repository;

import com.gupta.foodUp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String username);

}
