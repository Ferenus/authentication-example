package com.huro.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainPageController {

    @GetMapping("/tasks")
    public String getTasks() {
        return "It is working!";
    }

}