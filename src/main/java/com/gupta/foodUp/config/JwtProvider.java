//package com.gupta.foodUp.config;
//
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.crypto.SecretKey;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//public class JwtProvider {
//
//    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
//
//    public String generateToken(Authentication auth){
//        Collections<? extends GrantedAuthority> authorities = auth.getAuthorities();
//        String roles = populateAuthorities(authorities);
//    }
//
//    private String populateAuthorities(Collections authorities) {
//        Set<String> auths = new HashSet<>();
//
//        for(GrantedAuthority authority : authorities){
//            auths.add(au)
//        }
//    }
//
//}
