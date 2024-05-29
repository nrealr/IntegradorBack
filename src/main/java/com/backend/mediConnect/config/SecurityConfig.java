package com.backend.mediConnect.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.mediConnect.config.jwt.JwtAccessDeniedError;
import com.backend.mediConnect.config.jwt.JwtAuthenticationError;
import com.backend.mediConnect.config.jwt.JwtRequestFilter;
import com.backend.mediConnect.service.impl.UserDetailService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtAuthenticationError jwtAuthenticationError;
    @Autowired
    private JwtAccessDeniedError jwtAccessDeniedError;

    @Bean
    JwtRequestFilter jwtRequestFilter(){
        return new JwtRequestFilter();
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(authConfig -> {
            authConfig.requestMatchers("/users/login").permitAll();
            authConfig.requestMatchers("/users/register").permitAll();
            authConfig.requestMatchers("/doctors/list").permitAll();
            authConfig.requestMatchers("/doctors/{id}").permitAll();
            authConfig.requestMatchers("/features/{id}").permitAll();
            authConfig.requestMatchers("/specialties/list").permitAll();
            authConfig.requestMatchers("/specialties/{id}").permitAll();

            authConfig.anyRequest().authenticated();
        })
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())

                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationError)
                            .accessDeniedHandler(jwtAccessDeniedError);
                }).sessionManagement(sessionManagement -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        http.userDetailsService(userDetailService);
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
