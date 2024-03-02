package com.jwtauth.Auth.DTO

class AuthenticateDTO {

    String username
    String password

    AuthenticateDTO() {}

    AuthenticateDTO(String username, String password) {
        this.username = username
        this.password = password
    }
}
