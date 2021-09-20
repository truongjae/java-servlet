package com.nguyengiatruong.repository;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.Role;
import com.nguyengiatruong.orm.repository.JpaRepository;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByUserId(long userId);
}
