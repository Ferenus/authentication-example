package com.huro.rest;

import com.google.common.collect.ImmutableMap;
import com.huro.rest.dto.UserDto;
import com.huro.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;

    LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<Map<String, ArrayList<String>>> newUser(@RequestBody UserDto newEmployee) {
        ArrayList<String> emailErrors = validateEmail(newEmployee.getEmail());
        ArrayList<String> passwordErrors = validatePassword(newEmployee.getPassword());
        if (emailErrors.size() == 0 && passwordErrors.size() == 0) {
            userService.create(newEmployee.getEmail(), newEmployee.getPassword());
            return new ResponseEntity<>( HttpStatus.CREATED );
        }
        Map<String, ArrayList<String>> errors = ImmutableMap.of("emailErrors", emailErrors, "passwordErrors", passwordErrors);
        return new ResponseEntity<>( errors, HttpStatus.BAD_REQUEST );
    }

    private ArrayList<String> validateEmail(String email) {
        ArrayList<String> emailErrors = new ArrayList<String>();
        if (email != null) {
            if (!email.matches("\\S+@\\S+\\.\\S+")) {
                emailErrors.add("\"" + email + "\" is not a valid Email address.");
            }
        } else {
            emailErrors.add("Email is required.");
        }
        return emailErrors;
    }

    private ArrayList<String> validatePassword(String password) {
        ArrayList<String> passErrors = new ArrayList<String>();
        if (password != null) {
            if (password.length() < 8) {
                passErrors.add("Your password must be at least 8 characters");
            }
            if (!password.matches(".*[a-zA-Z]+.*")) {
                passErrors.add("Your password must contain at least one letter.");
            }
            if (!password.matches(".*[0-9]+.*")) {
                passErrors.add("Your password must contain at least one digit.");
            }
        } else {
            passErrors.add("Password is required.");
        }
        return passErrors;
    }
}
