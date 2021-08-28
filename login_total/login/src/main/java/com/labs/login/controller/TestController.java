package com.labs.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test")
public class TestController {
    @GetMapping(path = "/token/valid", consumes = "*/*", produces = "*/*")
    public String tokenValidtest() {
        return "token check";
    }
}
