package com.mekn.demo.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.mekn.demo.security.ApplicationUserRole.*;

@Configuration
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "index", "/css/*","/js/*").permitAll()
                        .requestMatchers("/api/**").hasRole(STUDENT.name())
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    protected UserDetailsService userDetailsService(){
       UserDetails mekUser = User.builder()
                .username("mek")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name()) //ROLE_STUDENT
                .build();

        UserDetails martinUser = User.builder()
                .username("martin")
                .password(passwordEncoder.encode("password123"))
                .roles(ADMIN.name()) //ROLE_ADMIN
                .build();

        UserDetails timUser = User.builder()
                .username("tim")
                .password(passwordEncoder.encode("password1234"))
                .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .build();


        return new InMemoryUserDetailsManager(
                mekUser,
                martinUser,
                timUser
        );
    }
}
