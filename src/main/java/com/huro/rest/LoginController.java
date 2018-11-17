package com.huro.rest;

import com.huro.rest.dto.UserDto;
import com.huro.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;

    LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public void newUser(@RequestBody UserDto newEmployee) {
        userService.create(newEmployee.getEmail(), newEmployee.getPassword());
    }
}
