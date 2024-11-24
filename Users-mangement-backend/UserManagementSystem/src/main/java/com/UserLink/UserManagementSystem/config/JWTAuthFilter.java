/*
It is responsible for JWT authentication in a spring security context
When a user authenticates successfully, Spring Security creates an Authentication object and stores it in the SecurityContext.
This context is then accessible throughout the application, ensuring that security information is always available for decision-making processes
such as authorization checks.
SecurityContextHolder provide access to security context.
 */

package com.UserLink.UserManagementSystem.config;

import com.UserLink.UserManagementSystem.service.JWTUtils;
import com.UserLink.UserManagementSystem.service.OurUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Marks this class as a Spring component
public class JWTAuthFilter extends OncePerRequestFilter { // it runs once per request.

    @Autowired  // Injects an instance of JWTUtils
    private JWTUtils jwtUtils;

    @Autowired  // Injects an instance of OurUserDetailsService
    private OurUserDetailsService ourUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");  // Get the Authorization header from the request
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || authHeader.isBlank()) {  // If the header is missing or empty
            filterChain.doFilter(request, response);  // Continue to the next filter
            return;
        }

        jwtToken = authHeader.substring(7);  // Remove "Bearer " prefix to get the token
        userEmail = jwtUtils.extractUsername(jwtToken);  // Extract the username (email) from the token

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {  // If username is found and no user is authenticated yet
            UserDetails userDetails = ourUserDetailsService.loadUserByUsername(userEmail);  // Load user details

            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {  // Check if the token is valid
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();  // Create an empty security context
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()  // Create an authentication token with user details
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Set details from the request
                securityContext.setAuthentication(token);  // Set the authentication in the security context
                SecurityContextHolder.setContext(securityContext);  // Set the security context
            }
        }

        filterChain.doFilter(request, response);  // Continue to the next filter
    }
}
