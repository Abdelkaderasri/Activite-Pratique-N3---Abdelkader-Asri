package org.enset.spring_mvc_thymeleaf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        String encodedPassword = passwordEncoder.encode("1234");
        System.out.println(encodedPassword);
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                User.withUsername("admin").password(encodedPassword).roles("USER","ADMIN").build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Custom login page URL
                                .permitAll() // Allow everyone to access the login page
                                .defaultSuccessUrl("/index", true) // Redirects to /index after login
                )
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/deletePatient/**").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                ) .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/notAuthorized")
                ).rememberMe(rememberMeConfigurer -> {
                    // Configure remember-me as needed within this block
                    // Example: Set token validity period
                     rememberMeConfigurer.tokenValiditySeconds(86400); // 24 hours
                })
                .build();

    }
}
