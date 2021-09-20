package com.nguyengiatruong.service.impl;

import com.nguyengiatruong.bean.annotation.Autowire;
import com.nguyengiatruong.bean.annotation.Service;
import com.nguyengiatruong.entity.Role;
import com.nguyengiatruong.repository.RoleRepository;
import com.nguyengiatruong.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowire
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> findRoleByUserId(long userId) {
        return (List<Role>) roleRepository.findByID(userId).orElse(new Role());
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll().collect(Collectors.toList());
    }
}
