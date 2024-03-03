package com.jwtauth.Auth.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User implements UserDetails {

    enum Role {
        USER("USER"),
        ADMIN("ADMIN")

        String value

        Role(String value) {
            this.value = value
        }

        String getValue() {
            return value
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID userId
    @Column(unique = true)
    String username
    String password
    String name
    @Enumerated(EnumType.STRING)
    Role role

    User() {}

    User(String username, String password, String name, Role role) {
        this.username = username
        this.password = password
        this.name = name
        this.role = role
    }


    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.value))
    }

    @Override
    String getPassword() {
        return password
    }

    @Override
    String getUsername() {
        return username
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}
