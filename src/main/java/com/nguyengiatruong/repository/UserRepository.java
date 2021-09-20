package com.nguyengiatruong.repository;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.User;
import com.nguyengiatruong.orm.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
