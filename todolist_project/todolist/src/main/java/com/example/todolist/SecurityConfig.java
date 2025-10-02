package com.example.todolist;

import com.example.todolist.service.AppUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AppUserDetailsServiceImpl appUserDetailsService;
    private final AuthEntryPoint exceptionHanler;
    private final AuthenticationFilter authenticationFilter;

    public SecurityConfig(AppUserDetailsServiceImpl appUserDetailsService, AuthEntryPoint authEntryPoint, AuthenticationFilter authenticationFilter) {
        this.appUserDetailsService = appUserDetailsService;
        this.exceptionHanler = authEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());    // BCryptPasswordEncoder는 스프링에서 권장하는 암호화 방식으로, 비밀번호를 **단방향 해시(BCrypt)**로 처리
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean   //AuthenticationManager 사용자의 아이디/비밀번호 같은 인증 정보를 받아서 **인증(로그인 검증)**을 처리하는 역할 (쉽게 말해: 로그인 시 "이 계정/비번 맞냐?")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(HttpMethod.POST,"/login").permitAll()
                        .anyRequest().authenticated()
                )
                // 필터 및 예외 처리기를 추가한 부분
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex->ex.authenticationEntryPoint(exceptionHanler));

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){           //CORS(Cross-Origin Resource Sharing) 정책을 설정하는 Bean 메서드
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));

        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**",config);
        return source;
    }
}
