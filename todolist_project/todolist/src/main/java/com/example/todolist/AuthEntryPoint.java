package com.example.todolist;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

// 로그인 실패했을때 401을 띠우는 과정.
// 다른 로직에 호출이 필요한것이 아닌 postman이나 브라우저에 적용되게 하는 로직이다.
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);                // 401 오류 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);              //
        PrintWriter writer = response.getWriter();                              // postman에서 error메시지가 출력될 수 있도록 해주는곳.
        writer.println("Error : " + authException.getMessage());
    }
}
