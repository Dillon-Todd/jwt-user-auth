package com.jwtauth.Auth.controllers

import com.jwtauth.Auth.DTO.AuthenticateDTO
import com.jwtauth.Auth.DTO.RegisterDTO
import com.jwtauth.Auth.models.AuthResponse
import com.jwtauth.Auth.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController {

    @Autowired AuthService authService

    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(@RequestBody RegisterDTO req) {
        return ResponseEntity.ok(authService.register(req))
    }

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody AuthenticateDTO req) {
        return ResponseEntity.ok(authService.login(req))
    }

    @GetMapping("/public")
    String publicEndpoint() {
        return "this is a public endpoint"
    }

    @GetMapping("/secure")
    String secureEndpoint() {
        return "this is a secure endpoint"
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    String user() {
        return "you have a user role"
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    String admin() {
        return "you have an admin role"
    }

}
