package com.springboot.stocks;

import org.apache.catalina.servlets.WebdavServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    ServletRegistrationBean<WebdavServlet> h2ServletRegistration() {
        ServletRegistrationBean<WebdavServlet> registrationBean = new ServletRegistrationBean<>(new WebdavServlet());

        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
}
