package com.example.demo.config;

import com.example.demo.model.enums.UserRoleEnum;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.impl.VetClinicUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(

                authorizeRequests -> authorizeRequests

                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                        .requestMatchers("/","/index", "/login", "/register").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/admin/user").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/vets/add").hasRole(UserRoleEnum.ADMIN.name())
                        .requestMatchers("/medical-records/add").hasRole(UserRoleEnum.ADMIN.name())
//                        .requestMatchers("/appointments/add").hasRole(UserRoleEnum.USER.name())
//                        .requestMatchers("/pets/add").hasRole(UserRoleEnum.USER.name())
                        .anyRequest().authenticated()
        ).formLogin(
                formLogin -> {
                    formLogin

                            .loginPage("/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");
                }
        ).logout(
                logout -> {
                    logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        )
        .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new VetClinicUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
