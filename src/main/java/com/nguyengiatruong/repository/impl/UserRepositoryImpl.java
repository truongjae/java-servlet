package com.nguyengiatruong.repository.impl;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.User;
import com.nguyengiatruong.orm.repository.impl.AbstractQuerySimple;
import com.nguyengiatruong.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends AbstractQuerySimple<User,Long> implements UserRepository {
}
