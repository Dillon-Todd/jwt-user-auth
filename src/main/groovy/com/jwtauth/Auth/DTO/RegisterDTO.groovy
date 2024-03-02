package com.jwtauth.Auth.DTO

import com.jwtauth.Auth.models.User

class RegisterDTO {

    String username
    String password
    String name
    User.Role role

    RegisterDTO() {}

    RegisterDTO(String username, String password, String name, User.Role role) {
        this.username = username
        this.password = password
        this.name = name
        this.role = role
    }
}
