package com.nguyengiatruong.service.impl;

import com.nguyengiatruong.bean.annotation.Autowire;
import com.nguyengiatruong.bean.annotation.Service;
import com.nguyengiatruong.entity.User;
import com.nguyengiatruong.exception.ObjectNotFoundException;
import com.nguyengiatruong.model.request.user.AuthRequest;
import com.nguyengiatruong.repository.UserRepository;
import com.nguyengiatruong.repository.specification.UserSpecification;
import com.nguyengiatruong.service.UserService;

import java.text.MessageFormat;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowire
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User auth(AuthRequest authRequest) {
        Optional<User> user = userRepository.findAll(UserSpecification.authFilter(authRequest));
        System.out.println(user.stream().toArray());
        user.orElseThrow(()->new ObjectNotFoundException(MessageFormat.format("user not found with condition: {0} ",authRequest)));
        return user.get();
    }
}
