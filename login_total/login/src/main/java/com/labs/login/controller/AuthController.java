package com.labs.login.controller;

import com.labs.login.component.CookieComponent;
import com.labs.login.db.Auth;
import com.labs.login.dto.auth.LoginRequestDto;
import com.labs.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private CookieComponent cookieComponent;

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Auth> loginApi(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok().body(authService.login(loginRequestDto));
    }

    @PostMapping(path = "/logout", consumes = "*/*", produces = "*/*")
    public ResponseEntity<String> logoutApi(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(
                this.authService.logout(this.cookieComponent.userIdInCookie(httpServletRequest))
        );
    }
}
