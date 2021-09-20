package com.nguyengiatruong.controller;

import com.nguyengiatruong.bean.BeanFactory;
import com.nguyengiatruong.model.request.user.AuthRequest;
import com.nguyengiatruong.security.Authentication;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class AuthController extends BaseController{

    private final Authentication authentication;

    public AuthController() {
        this.authentication = (Authentication) BeanFactory.beans.get("authentication");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rs = req.getRequestDispatcher("views/Login.jsp");
        rs.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AuthRequest authRequest = mapToModel(req,AuthRequest.class);
        String url = this.authentication.authenticate(authRequest,req);
        resp.sendRedirect(url);
    }
}
