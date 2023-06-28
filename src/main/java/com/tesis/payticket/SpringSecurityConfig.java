package com.tesis.payticket;

import com.tesis.payticket.auth.handler.LoginSuccesHandler;
import com.tesis.payticket.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccesHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailService;

    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                    try {
                        authz
                                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/index","/register").permitAll()
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
                    formLogin.successHandler(successHandler);
                    formLogin.loginPage("/login");
                    formLogin.permitAll();
                })
                .logout((logout) -> logout.permitAll())
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/error_403"));


        return http.build();
    }


    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);

        return authBuilder.build();
    }

}
