package com.mycompany.jwt.controller;

import com.mycompany.jwt.model.JwtRequest;
import com.mycompany.jwt.model.JwtResponse;
import com.mycompany.jwt.model.UserRequest;
import com.mycompany.jwt.repository.UserRepository;
import com.mycompany.jwt.service.CustomUserDetailsService;
import com.mycompany.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserRequest> register(@RequestBody UserRequest userRequest) {
        UserRequest userRequest1 = customUserDetailsService.register(userRequest);

        return new ResponseEntity<UserRequest>(userRequest1, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {
        // authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwtToken);

        return ResponseEntity.ok(jwtResponse);
//        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public UserRequest getCurrentUser(Principal principal) {
        return (UserRequest) customUserDetailsService.loadUserByUsername(principal.getName());
    }
}
