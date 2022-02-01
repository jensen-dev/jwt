package com.mycompany.jwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // this can be from database by calling the repository
    // this method actually does the validation for user existence
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // here we can make a db call with the help of repository and do the validation
        if (username.equalsIgnoreCase("john")) {
            return new User("john", "secret", new ArrayList<>()); // assume this is returned from db upon success
        } else {
            throw new UsernameNotFoundException("user does not exist");
        }
    }
}
