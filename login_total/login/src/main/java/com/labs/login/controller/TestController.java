package com.labs.login.controller;

import com.labs.login.component.StateValueComponent;
import com.labs.login.exception.UnAuthException;
import com.labs.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private StateValueComponent stateValueComponent;

    @GetMapping(path = "/token/valid", consumes = "*/*", produces = "*/*")
    public String tokenValidtest() {
        return "token check";
    }

    @GetMapping(path = "/role/user", consumes = "*/*", produces = "*/*")
    public String userRoleTest(HttpServletRequest httpServletRequest) {
        if (this.userService.checkRoleUserIdInCookie(httpServletRequest, stateValueComponent.getUserRole())) {
            return "user role check";
        }
        throw new UnAuthException("user role un auth");
    }

    @GetMapping(path = "/role/admin", consumes = "*/*", produces = "*/*")
    public String adminRoleTest(HttpServletRequest httpServletRequest) {
        if (this.userService.checkRoleUserIdInCookie(httpServletRequest, stateValueComponent.getAdminRole())) {
            return "admin role check";
        }
        throw new UnAuthException("admin role un auth");
    }

    @GetMapping(path = "/role/super-admin", consumes = "*/*", produces = "*/*")
    public String sueprAdminRoleTest(HttpServletRequest httpServletRequest) {
        if (this.userService.checkRoleUserIdInCookie(httpServletRequest, stateValueComponent.getSuperAdminRole())) {
            return "super admin role check";
        }
        throw new UnAuthException("super admin role un auth");
    }
}
