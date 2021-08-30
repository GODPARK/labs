package com.labs.login.component;

import com.labs.login.exception.BadRequestException;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class CookieComponent {
    public UUID userIdInCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    return UUID.fromString(cookie.getValue());
                }
            }
            throw new BadRequestException("user id is null in cookie");
        } else {
            throw new BadRequestException("cookie is null");
        }
    }

    public String authTokenInCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth_token")) {
                    return cookie.getValue();
                }
            }
            throw new BadRequestException("auth token is null in cookie");
        } else {
            throw new BadRequestException("cookie is null");
        }
    }
}
