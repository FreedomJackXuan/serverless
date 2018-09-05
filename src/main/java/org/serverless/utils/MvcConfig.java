//package org.serverless.utils;
//
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.config.annotation.*;
//
//import javax.servlet.Servlet;
//
//
//@Configuration
//
//public class MvcConfig extends WebMvcConfigurationSupport {
//    @Bean
//    public WebMvcConfigurationSupport webMvcConfigurationSupport() {
//        WebMvcConfigurationSupport support = new WebMvcConfigurationSupport() {
////            @Override
////            protected void addViewControllers(ViewControllerRegistry registry) {
////                registry.addViewController("/login.html").setViewName("login");
////                registry.addViewController("/index.html").setViewName("index");
////                // registry.addViewController("/login.html").setViewName("login");
////            }
//
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                //registry.addResourceHandler("/resources/static/**").addResourceLocations("classpath:/static/");
//                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/resources/static/");
//                super.addResourceHandlers(registry);
//            }
//        };
//        return support;
//    }
//}
