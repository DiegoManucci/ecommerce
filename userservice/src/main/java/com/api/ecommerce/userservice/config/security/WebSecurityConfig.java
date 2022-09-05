package com.api.ecommerce.userservice.config.security;

import com.api.ecommerce.userservice.config.security.filters.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final AuthenticationFilter authenticationFilter;

    public WebSecurityConfig(AuthenticationConfiguration authenticationConfiguration, AuthenticationEntryPointImpl authenticationEntryPoint, AuthenticationFilter authenticationFilter) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/auth/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        //return authenticationConfiguration.getAuthenticationManager();
        return new AuthenticationManagerImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProviderImpl();
    }

}
