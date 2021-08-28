package com.labs.login.controller;

import com.labs.login.db.User;
import com.labs.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/uuid/{userId}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<User> userByUserIdApi(@PathVariable(value = "userId") String userId) {
        return ResponseEntity.ok().body(this.userService.userByStringUserId(userId, false));
    }

    @GetMapping(path = "/account/{userAccount}", consumes = "*/*", produces = "application/json")
    public ResponseEntity<User> userByUserAccountApi(@PathVariable(value = "userAccount") String userAccount) {
        return ResponseEntity.ok().body(this.userService.userByUserAccount(userAccount, false));
    }

    @PostMapping(path = "/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> signUpApi(@RequestBody @Valid User user) {

        return ResponseEntity.ok().body(userService.signUp(user));
    }

    @PostMapping(path = "/sign-out", consumes = "*/*", produces = "application/json")
    public ResponseEntity<User> signOutApi(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        String userId = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    userId = cookie.getValue();
                }
            }
        }
        return ResponseEntity.ok().body(this.userService.signOut(UUID.fromString(userId)));
    }
}
