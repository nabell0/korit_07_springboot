package com.example.todolist.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    static final long EXPIRATION = 86400000;
    static final String PREFIX = "Bearer";
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); //비밀키 생성

    public String getToken(String username){                //로그인 할때 토큰 생성
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(key)
                .compact();             // build(); 대신 compact()로 마무리
        return token;
    }
    public String getAuthUser(HttpServletRequest request){                  // request를 할때 토큰을 확인하는 과정
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);        // Authorization 헤더 읽기 -> postman 상에서 Header -> Authorization -> key - Authorization -> value-생성된 토큰 Bearer 뒤 복사
        if(token != null){
            String user = Jwts.parser()
                    .setSigningKey(key)
                    .build()

                    .parseClaimsJws(token.replace(PREFIX,"").trim())
                    .getBody()
                    .getSubject();
            if(user != null) return user;
        }
        return null;
    }
}
