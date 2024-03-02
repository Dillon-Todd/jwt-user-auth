package com.jwtauth.Auth.models

class AuthResponse {

    String token
    UUID userId

    AuthResponse(String token, UUID userId) {
        this.token = token
        this.userId = userId
    }
}
