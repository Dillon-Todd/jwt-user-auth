package com.jwtauth.Auth.services

import com.jwtauth.Auth.models.User
import com.jwtauth.Auth.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService implements UserDetailsService {

    @Autowired UserRepository userRepository

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow { new UsernameNotFoundException("User not found") }
    }
}
