package com.gupta.foodUp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

// NOTE: JwtConstant class is assumed to contain public static final String fields for SECRET_KEY and JWT_HEADER

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Extract the JWT from the header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER); // e.g., "Authorization"

        if (jwt != null) {
            // Check if the header starts with "Bearer " and remove it
            if (jwt.startsWith("Bearer ")) {
                jwt = jwt.substring(7);
            }

            try {
                // 2. Define the SecretKey using the constant
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                // 3. Parse and validate the JWT to get the Claims
                Claims claims = Jwts.parser()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                // 4. Extract user details from Claims
                String email = String.valueOf(claims.get("email"));
                // The authorities are typically stored as a string or a list of strings
                String authorities = String.valueOf(claims.get("authorities")); // ROLE_CUSTOMER,ROLE_ADMIN

                // 5. Create Authentication object
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, auths);

                // 6. Set the Authentication object in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // Handle token validation failure (e.g., expired, invalid signature)
                throw new BadCredentialsException("Invalid Token received!", e);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

}