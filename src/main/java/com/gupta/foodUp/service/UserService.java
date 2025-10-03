package com.gupta.foodUp.service;

import com.gupta.foodUp.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
