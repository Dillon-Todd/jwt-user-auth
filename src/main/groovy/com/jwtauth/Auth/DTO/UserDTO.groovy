package com.jwtauth.Auth.DTO

import com.jwtauth.Auth.models.User

class UserDTO {

    UUID userId
    String username
    String name
    User.Role role

    UserDTO() {}

    UserDTO(UUID userId, String username, String name, User.Role role) {
        this.userId = userId
        this.username = username
        this.name = name
        this.role = role
    }

}
