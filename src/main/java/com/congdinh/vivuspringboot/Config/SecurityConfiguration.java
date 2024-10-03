package com.congdinh.vivuspringboot.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/css/**", "/js/**", "/media/**").permitAll()
                .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/edit-profile", true)
                .failureUrl("/login?error"))
            .logout(logout -> logout
                .logoutUrl("/logout")  // URL for logout
                .logoutSuccessUrl("/login")  // Redirect to login page after logout
                .invalidateHttpSession(true)  // Invalidate session after logout
                .deleteCookies("JSESSIONID"))
                .rememberMe(rememberMe ->rememberMe .key("uniqueAndSecret").
                tokenValiditySeconds(86400).
                rememberMeParameter("remember-me"))  // Delete session cookies
            .exceptionHandling(exceptionHandling ->
                exceptionHandling.accessDeniedPage("/access-denied"))
                .httpBasic(Customizer.withDefaults());  // Custom access denied page
        return http.build();
    }

}
