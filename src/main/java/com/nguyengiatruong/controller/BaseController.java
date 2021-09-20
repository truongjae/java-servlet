package com.nguyengiatruong.controller;

import com.google.gson.Gson;
import com.nguyengiatruong.exception.InternalServerException;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class BaseController extends HttpServlet {
    protected Gson gson;
    public BaseController(){
        this.gson = new Gson();
    }
    public static <T> T mapToModel(HttpServletRequest request, Class<T> clazz){
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
//            org.apache.commons.beanutils.BeanUtils.populate(t,request.getParameterMap());
            BeanUtils.populate(t,request.getParameterMap());
            return t;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new InternalServerException(e.getCause().toString());
        }
    }

//    public static <T> T convertToModel(HttpServletRequest request, Class<T> tClass){
//        StringBuilder json = new StringBuilder();
//        String line;
//        try {
//            while ((line=request.getReader().readLine()) != null){
//                json.append(line);
//            }
//            return gson.fromJson(json.toString(),tClass);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
