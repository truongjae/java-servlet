package com.nguyengiatruong.listener;

import com.nguyengiatruong.bean.BeanFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BeanFactory beanFactory = new BeanFactory();
    }
}
