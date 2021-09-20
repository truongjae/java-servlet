package com.nguyengiatruong.security;

import com.nguyengiatruong.model.request.user.AuthRequest;

import javax.servlet.http.HttpServletRequest;

public interface Authentication {
    String authenticate(AuthRequest authRequest, HttpServletRequest httpServletRequest);
}
