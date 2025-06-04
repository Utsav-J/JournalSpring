package com.utsavj.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeatlhCheck {
    @GetMapping("health-check")
    public String check() {
        return "All good";
    }
}
