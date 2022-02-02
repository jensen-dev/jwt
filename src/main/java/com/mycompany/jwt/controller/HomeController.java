package com.mycompany.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @PreAuthorize("hasRole('ADMIN')") // @PreAuthorize("hasAnyRole('ADMIN', 'USER')
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // @PreAuthorize("hasAnyAuthority('DELETE_AUTHORITY', 'UPDATE_AUTHORITY')
    @GetMapping("/hello")
    public String helloWorld() {
        return "hello world from HomeController";
    }
}
