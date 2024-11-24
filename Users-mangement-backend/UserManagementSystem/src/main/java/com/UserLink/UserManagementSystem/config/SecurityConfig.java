package com.UserLink.UserManagementSystem.config;
import com.UserLink.UserManagementSystem.service.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indicate that this is a configuration class for Spring
@EnableWebSecurity  // Enable Spring Security for the application
public class SecurityConfig {

    // Autowire the user details service for fetching user data
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    // Autowire the JWT authentication filter
    @Autowired
    private JWTAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Configure HTTP security
        httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .cors(Customizer.withDefaults()) // Enable CORS with default settings
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**", "/public/**").permitAll() // Allow unauthenticated access to /auth and /public endpoints
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN") // Restrict /admin endpoints to users with ADMIN authority
                        .requestMatchers("/user/**").hasAnyAuthority("USER") // Restrict /user endpoints to users with USER authority
                        .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN", "USER") // Restrict /adminuser endpoints to users with ADMIN or USER authority
                        .anyRequest().authenticated()) // Require authentication for any other requests
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use stateless session management
                .authenticationProvider(authenticationProvider()) // Set the authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add the JWT authentication filter before the UsernamePasswordAuthenticationFilter
        return httpSecurity.build(); // Build and return the security filter chain
    }

    // Define the authentication provider bean
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService); // Set the user details service
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Set the password encoder
        return daoAuthenticationProvider; // Return the configured authentication provider
    }

    // Define the password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    // Define the authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Get the authentication manager from the configuration
    }
}
