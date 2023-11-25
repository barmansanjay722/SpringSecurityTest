package com.spring.security.test.SpringSecurityTest.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails normalUser = User
                .withUsername("sanjay")
                .password(passwordEncoder().encode("password"))
                .roles("NORMAL")
                .build();

        UserDetails adminUser = User
                .withUsername("sanjay1")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(normalUser,adminUser);
        return inMemoryUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authr) -> authr
                .requestMatchers("/home/admin")
                .hasRole("ADMIN")
                .requestMatchers("/home/normal")
                .hasRole("NORMAL")
                .requestMatchers("/home/public")
                .permitAll()
                .anyRequest()
                .authenticated()
        ).httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
