package com.huro.rest;

import com.huro.model.entity.HuroUser;
import com.huro.model.repository.UserRepository;
import com.huro.rest.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserRepository repository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    LoginController(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/api/users/register")
    public void newUser(@RequestBody UserDto newEmployee) {
        newEmployee.setPassword(bCryptPasswordEncoder.encode(newEmployee.getPassword()));
        repository.save(new HuroUser(newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getUsername(), newEmployee.getPassword(), "USER"));
    }
}
