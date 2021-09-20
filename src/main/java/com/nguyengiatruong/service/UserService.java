package com.nguyengiatruong.service;

import com.nguyengiatruong.entity.User;
import com.nguyengiatruong.model.request.user.AuthRequest;

public interface UserService {
    User auth(AuthRequest authRequest);
}
