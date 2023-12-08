package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Log the request URI and timestamp before the request is processed
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Timestamp: " + LocalDateTime.now());
        return true;
    }
}
