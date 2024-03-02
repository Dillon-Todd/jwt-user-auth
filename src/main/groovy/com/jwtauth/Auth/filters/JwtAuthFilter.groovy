package com.jwtauth.Auth.filters

import com.jwtauth.Auth.models.User
import com.jwtauth.Auth.services.JwtService
import com.jwtauth.Auth.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired JwtService jwtService
    @Autowired UserService userService

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        String token = authHeader.substring(7)
        String username = jwtService.extractUsername(token)

        if (username != null && SecurityContextHolder.context.authentication == null) {
            User user = userService.loadUserByUsername(username)
            if (jwtService.isValid(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, user.authorities
                )
                authToken.details = new WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.context.authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}
