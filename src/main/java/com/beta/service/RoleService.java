package com.beta.service;

import com.beta.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByRoleName(String roleName);
}
