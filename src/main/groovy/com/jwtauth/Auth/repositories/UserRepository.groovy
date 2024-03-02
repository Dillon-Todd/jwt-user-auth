package com.jwtauth.Auth.repositories

import com.jwtauth.Auth.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username)
}