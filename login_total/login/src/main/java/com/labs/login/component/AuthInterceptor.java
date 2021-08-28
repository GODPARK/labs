package com.labs.login.component;

import com.labs.login.exception.UnAuthException;
import com.labs.login.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            String userId = "";
            String authToken = "";
            for ( Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    authToken = cookie.getValue();
                }
                if (cookie.getName().equals("user_id")) {
                    userId = cookie.getValue();
                }
            }
            if (userId.isBlank() || authToken.isBlank()) throw new UnAuthException("cookie not have token or user id");
            return this.authService.tokenCheck(UUID.fromString(userId), authToken);
        }
        else {
            throw new UnAuthException("no cookie");
        }
    }
}
