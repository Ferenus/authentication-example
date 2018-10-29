package com.huro.rest;

import com.huro.model.entity.User;
import com.huro.model.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserRepository repository;

    LoginController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/users/register")
    User newUser(@RequestBody User newEmployee) {
        return repository.save(newEmployee);
    }
}
