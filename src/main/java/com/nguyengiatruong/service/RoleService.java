package com.nguyengiatruong.service;

import com.nguyengiatruong.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRoleByUserId(long userId);

    List<Role> getAll();
}
