package com.greendrive.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Public — no login required
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    // Requires login and role USER
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }

    // Requires login and role ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin Content.";
    }
}
