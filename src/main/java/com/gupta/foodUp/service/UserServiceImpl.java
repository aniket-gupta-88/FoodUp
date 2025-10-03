package com.gupta.foodUp.service;

import com.gupta.foodUp.config.JwtProvider;
import com.gupta.foodUp.model.User;
import com.gupta.foodUp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
       String email =  jwtProvider.getEmailFromJwtToken(jwt);
       User user = userRepository.findByEmail(email);
       return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw  new Exception("User not found");
        }

        return user;
    }
}
