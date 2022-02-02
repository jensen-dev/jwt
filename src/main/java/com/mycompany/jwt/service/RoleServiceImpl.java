package com.mycompany.jwt.service;

import com.mycompany.jwt.entity.RoleEntity;
import com.mycompany.jwt.model.RoleRequest;
import com.mycompany.jwt.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleRequest createRole(RoleRequest role) {
        if (Objects.nonNull(role)) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleName(role.getRoleName());

            RoleEntity roleEntity1 = roleRepository.save(roleEntity);
            BeanUtils.copyProperties(roleEntity1, role);
            return role;
        } else {
            return null;
        }
    }

    @Override
    public List<RoleRequest> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleRequest> roleRequests = new ArrayList<>();

        for (RoleEntity roleEntity : roleEntities) {
            RoleRequest roleRequest = new RoleRequest();
            roleRequest.setId(roleEntity.getId());
            roleRequest.setRoleName(roleEntity.getRoleName());

            roleRequests.add(roleRequest);
        }

        return roleRequests;
    }

    @Override
    public RoleRequest getRoleById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id).get();

        if (Objects.nonNull(roleEntity)) {
            RoleRequest roleRequest = new RoleRequest();
            roleRequest.setId(roleEntity.getId());
            roleRequest.setRoleName(roleEntity.getRoleName());

            return roleRequest;
        } else {
            return null;
        }
    }
}
