package com.jwtauth.Auth.services

import com.jwtauth.Auth.DTO.AuthenticateDTO
import com.jwtauth.Auth.DTO.RegisterDTO
import com.jwtauth.Auth.DTO.UserDTO
import com.jwtauth.Auth.models.AuthResponse
import com.jwtauth.Auth.models.User
import com.jwtauth.Auth.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired UserRepository userRepository
    @Autowired PasswordEncoder passwordEncoder
    @Autowired JwtService jwtService
    @Autowired AuthenticationManager authenticationManager

    AuthResponse register(RegisterDTO req) {
        User user = new User()
        user.username = req.username
        user.password = passwordEncoder.encode(req.password)
        user.name = req.name
        user.role = req.role
        userRepository.save(user)
        String token = jwtService.generateToken(user)
        return new AuthResponse(token, user.userId)
    }

    AuthResponse login(AuthenticateDTO req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
        )
        User user = userRepository.findByUsername(req.username).orElseThrow { new UsernameNotFoundException("User not found") }
        String token = jwtService.generateToken(user)
        return new AuthResponse(token, user.userId)
    }

    UserDTO getCurrentUser() {
        User user = SecurityContextHolder.context.authentication.principal as User
        return new UserDTO(user.userId, user.username, user.name, user.role)
    }
}
