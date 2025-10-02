package com.example.todolist;

import com.example.todolist.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

//매 요청마다 실행.

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;                            // 해석 검증 username을 꺼내려면 jwtService가 필요하기 때문.

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION);  // 토큰을 읽는 형태
        if(jws != null){    //
            String user = jwtService.getAuthUser(request);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
