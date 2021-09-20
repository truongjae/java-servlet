package com.nguyengiatruong.security;

import com.nguyengiatruong.bean.annotation.Autowire;
import com.nguyengiatruong.bean.annotation.Component;
import com.nguyengiatruong.entity.Role;
import com.nguyengiatruong.entity.User;
import com.nguyengiatruong.model.request.user.AuthRequest;
import com.nguyengiatruong.service.RoleService;
import com.nguyengiatruong.service.UserService;
import com.nguyengiatruong.exception.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationImpl implements Authentication{
    @Autowire
    private final UserService userService;
    @Autowire
    private final RoleService roleService;

    public AuthenticationImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public String authenticate(AuthRequest authRequest, HttpServletRequest httpServletRequest) {
        try{
            User user = userService.auth(authRequest);
//         List<String> roles = roleService.findRoleByUserId(user.getId()).stream().map(Role::getName).collect(Collectors.toList());
         List<String> roles = roleService.findRoleByUserId(user.getId()).stream().map(Role::getName).collect(Collectors.toList());

            if(roles.contains("ADMIN")){
                return "/admin";
            }
            return "/home";
        }catch (ObjectNotFoundException e){
            return "/login?message=tai khoan hoac mat khau khong dung";
        }

    }
}
