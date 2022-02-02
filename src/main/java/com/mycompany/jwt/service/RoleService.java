package com.mycompany.jwt.service;

import com.mycompany.jwt.model.RoleRequest;

import java.util.List;

public interface RoleService {

    public RoleRequest createRole(RoleRequest role);
    public List<RoleRequest> getAllRoles();
    public RoleRequest getRoleById(Long id);
}
