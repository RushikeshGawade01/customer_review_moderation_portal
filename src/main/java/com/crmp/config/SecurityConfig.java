package com.crmp.config;

import com.crmp.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security configuration.
 * Defines role-based access rules and login/logout behaviour.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Public resources
                .antMatchers("/static/**", "/login", "/h2-console/**").permitAll()
                // Customers can submit reviews
                .antMatchers("/reviews/new", "/reviews/submit").hasAnyRole("CUSTOMER", "MODERATOR", "ADMIN")
                // Moderation actions require MODERATOR or ADMIN
                .antMatchers("/reviews/*/moderate").hasAnyRole("MODERATOR", "ADMIN")
                // Dashboard for MODERATOR and ADMIN
                .antMatchers("/dashboard").hasAnyRole("MODERATOR", "ADMIN")
                // All authenticated users can view reviews
                .antMatchers("/reviews/**").authenticated()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
            // Allow H2 console (dev only)
            .csrf().ignoringAntMatchers("/h2-console/**")
            .and()
            .headers().frameOptions().sameOrigin(); // H2 console iframe
    }
}
