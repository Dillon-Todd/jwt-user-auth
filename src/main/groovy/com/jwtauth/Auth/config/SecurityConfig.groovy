package com.jwtauth.Auth.config

import com.jwtauth.Auth.filters.JwtAuthFilter
import com.jwtauth.Auth.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired UserService userService
    @Autowired JwtAuthFilter jwtAuthFilter

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf { csrfConfig -> csrfConfig.disable()}
        http.authorizeHttpRequests { matcherRegistry ->
            matcherRegistry.requestMatchers("api/login/**", "api/register/**", "api/public/**").permitAll()
            matcherRegistry.anyRequest().authenticated()
        }
        http.userDetailsService(userService)
        http.sessionManagement { sessionConfig ->
            sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        return http.build()
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) {
        return authConfig.authenticationManager
    }
}
