package com.example.jwt.config;

import com.example.jwt.config.auth.jwt.JwtAuthenticationFilter;
import com.example.jwt.filter.MyFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     private final CorsConfig config;

    @Override
    // 글로벌 조건 걸기
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MyFilter(), SecurityContextHolderAwareRequestFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션사용x
                .and()
                .addFilter(config.corsFilter()) // @CrossOrigin(인중x), 시큐리티 필터에 등록인증(o)
                .formLogin().disable() // 폼로그인 사용x
                .httpBasic().disable() // http 기본방식 사용 x
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager
                .authorizeRequests()
                .antMatchers("/api/v1/users/**")
                .access("hasRole('ROLE_USERS') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 권한없이
                ;
    }
}
