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
    private CookieComponent cookieComponent;

    public AuthInterceptor(AuthService authService, CookieComponent cookieComponent) {
        this.authService = authService;
        this.cookieComponent = cookieComponent;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        return this.authService.tokenCheck(
                this.cookieComponent.userIdInCookie(httpServletRequest),
                this.cookieComponent.authTokenInCookie(httpServletRequest)
        );
    }
}
