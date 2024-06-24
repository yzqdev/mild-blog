package com.site.blog.config.security;

import com.site.blog.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yzqde
 * @date time 2024/6/24 6:27
 * @modified By:
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final  JwtAuthenticationTokenFilter authenticationTokenFilter;



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
      CorsConfigurationSource corsConfigSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 http.cors(i->i.configurationSource(corsConfigSource()));
        http.csrf(i -> i.disable());
        http.formLogin(i -> i.disable());
        http.httpBasic(i -> i.disable());
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(  "/css/**","/upload/**", "/js/**", "/index.html", "/img/**", "/fonts/**" , "/verifyCode", "/swagger-ui/*", "/v2/api-docs/**", "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",

                    "/swagger-ui/**",
                    "/webjars/**", "/api-docs","/v2/auth/**","/v2/home/**").permitAll()
                    .requestMatchers("/v2/admin/**").authenticated();
            ;
        });
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(i -> i.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
