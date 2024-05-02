package com.example.gruppe4bilabonnement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/test")
    public String test() {
        return "hej";
    }
}
