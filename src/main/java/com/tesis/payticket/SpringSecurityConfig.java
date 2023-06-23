package com.tesis.payticket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("ADMIN", "USER")
                .build());
        manager.createUser(User
                .withUsername("user")
                .password(passwordEncoder().encode("12345"))
                .roles("USER")
                .build());
        return manager;
    }

   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/evento/listar").permitAll()
                                .requestMatchers("/uploads/**").hasAnyRole("USER")
                                .requestMatchers("/evento/ver/**").hasRole("USER")
                                .requestMatchers("/localidad/**").hasRole("ADMIN")
                                .requestMatchers("/evento/form/**").hasRole("ADMIN")
                                .anyRequest().authenticated();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .formLogin((formLogin) -> {
                    formLogin.loginPage("/login");
                    formLogin.permitAll();
                })
                .logout((logout) -> logout.permitAll())
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        return http.build();
    }


}
