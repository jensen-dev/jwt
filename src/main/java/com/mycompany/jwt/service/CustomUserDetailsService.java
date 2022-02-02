package com.mycompany.jwt.service;

import com.mycompany.jwt.entity.RoleEntity;
import com.mycompany.jwt.entity.UserEntity;
import com.mycompany.jwt.model.RoleRequest;
import com.mycompany.jwt.model.UserRequest;
import com.mycompany.jwt.repository.RoleRepository;
import com.mycompany.jwt.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserRequest register(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRequest, userEntity); // it does not do a deep copy

        Set<RoleEntity> roleEntities = new HashSet<>();
        // fetch every role from db based on role id and set this role into user entity roles
        for (RoleRequest request : userRequest.getRoles()) {
            Optional<RoleEntity> optRole = roleRepository.findById(request.getId());
            if (optRole.isPresent()) {
                roleEntities.add(optRole.get());
            }
        }

        userEntity.setRoles(roleEntities);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);

        BeanUtils.copyProperties(userEntity, userRequest);

        Set<RoleRequest> roleRequests = new HashSet<>();
        for (RoleEntity roleEntity : userEntity.getRoles()) {
            RoleRequest request = new RoleRequest();
            request.setRoleName(roleEntity.getRoleName());
            request.setId(roleEntity.getId());
            roleRequests.add(request);
        }
        userRequest.setRoles(roleRequests);

        return userRequest;
    }

    // this can be from database by calling the repository
    // this method actually does the validation for user existence
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        // here we can make a db call with the help of repository and do the validation
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("user does not exist");
        } else {
            UserRequest userRequest = new UserRequest();
            BeanUtils.copyProperties(userEntity, userRequest);

            Set<RoleRequest> roleRequests = new HashSet<>();
            for (RoleEntity roleEntity : userEntity.getRoles()) {
                RoleRequest request = new RoleRequest();
                request.setRoleName(roleEntity.getRoleName());
                request.setId(roleEntity.getId());
                roleRequests.add(request);
            }
            userRequest.setRoles(roleRequests);

            return userRequest;
        }
    }
}