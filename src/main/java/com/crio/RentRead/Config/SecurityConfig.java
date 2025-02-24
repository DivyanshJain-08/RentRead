package com.crio.RentRead.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig 
{

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf ->csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users/register", "/users/login").permitAll() // Public Endpoints
                // .requestMatchers(HttpMethod.GET, "/books/**").hasAnyRole("USER", "ADMIN") // Allow users and admins to view books
                // .requestMatchers(HttpMethod.POST, "/books/**").hasRole("ADMIN") 
                // .requestMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
                // .requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")// Only admins can add/update/delete books
                // .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-Only Endpoints
                .requestMatchers(HttpMethod.GET, "/books/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") 
                .requestMatchers(HttpMethod.POST, "/books/**").hasAuthority("ROLE_ADMIN") 
                .requestMatchers(HttpMethod.PUT, "/books/**").hasAuthority("ROLE_ADMIN") 
                .requestMatchers(HttpMethod.DELETE, "/books/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //  Ensures no session storage (stateless API)
            .httpBasic(Customizer.withDefaults()); // Enables Basic Auth

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
}
