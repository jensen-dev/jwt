package com.mycompany.jwt.controller;

import com.mycompany.jwt.model.RoleRequest;
import com.mycompany.jwt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/createRole")
    public RoleRequest createRole(@RequestBody RoleRequest role) {
        RoleRequest roleRequest = roleService.createRole(role);

        return roleRequest;
    }

    @GetMapping("/roles")
    public List<RoleRequest> getAllRoles() {
        List<RoleRequest> roleRequests = roleService.getAllRoles();

        return roleRequests;
    }
}
