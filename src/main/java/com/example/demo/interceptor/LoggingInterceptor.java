package com.example.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public class LoggingInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // Log the request URI and timestamp
//        System.out.println("Request URI: " + request.getRequestURI());
//        System.out.println("Timestamp: " + LocalDateTime.now());
//        return true;
//    }
}
