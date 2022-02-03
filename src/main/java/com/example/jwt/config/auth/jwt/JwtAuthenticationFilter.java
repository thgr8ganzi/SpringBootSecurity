package com.example.jwt.config.auth.jwt;

import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// lohin 요청해서 username, password 전송하면(POST)
// UsernamePasswordAuthenticationFilter 동작을함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)throws AuthenticationException{
        System.out.println("로그인시도중");
        // 1. username, password 받기
        try {
            ObjectMapper om = new ObjectMapper();
            Users users = om.readValue(request.getInputStream(), Users.class);
            System.out.println(users);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword());

            //principalDetailsService 에 loadUserByUsername 함수가 실행
            // 정상이면 authentication 이 리턴됨 DB 에 있는 username 과 password 가 일치 한다
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // authentication 객체가 session 영역에 저장됨. => 로그인이 되었다는 뜻.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUsername());

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 정상인지 로그인 시도 해봄 attemptAuthentication 로 로그인시도를 하면
        // principalDetailsService 가 호출 loadUserByUsername() 함수 실행

        // principalDetails 세션에 담고(권한관리 위해서)

        // jwt 토큰을 담아서 응답해주면 됨
        return null;
    }
}
