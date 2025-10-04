package com.gupta.foodUp.repository;

import com.gupta.foodUp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
