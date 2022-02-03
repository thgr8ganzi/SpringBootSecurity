package com.example.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding("UTF-8");
        // 토큰: cos id, pw 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
        // 요청할때마다 header 에 Authorization 에 value 값으로 토큰을 가지고 옴
        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰인지 검증하면 됨 (RSA, HS256)
        if(request.getMethod().equals("POST")) {
            System.out.println("POST요청됨");
            String headerAuth = request.getHeader("Authorization");
            System.out.println(headerAuth);
            System.out.println("필터1");
            if(headerAuth.equals("cos")){
                filterChain.doFilter(request, response);
            }else{
                PrintWriter printWriter = response.getWriter();
                System.out.println("인증안됨");
            }
        }

        filterChain.doFilter(request, response);
    }
}
