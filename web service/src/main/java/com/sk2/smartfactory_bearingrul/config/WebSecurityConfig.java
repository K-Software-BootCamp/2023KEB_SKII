package com.sk2.smartfactory_bearingrul.config;

import com.sk2.smartfactory_bearingrul.util.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 정적인 파일에 대한 요청들
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/file/**",
            "/css/**", "/js/**", "/images/**", "/*.ico",
            "/", "/signup"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable() // Cross-Site Request Forgery(CSRF) 공격 방지 기능을 비활성화. RESTful API에서는 일반적으로 CSRF 토큰을 사용하지 않으므로 비활성화
                .httpBasic().disable() // HTTP 기본 인증 사용 안 함. HTTP 기본 인증은 사용자 이름과 비밀번호를 평문으로 전송하여 보안에 취약
                .formLogin().disable() // 폼 기반 로그인 사용 안 함. 일반적인 웹 애플리케이션에서는 폼 기반 로그인을 사용하여 사용자의 자격 증명을 받지만, RESTful API에서는 토큰 기반의 인증을 사용하는 것이 일반적
                .logout().disable() // 로그아웃 사용 안 함. RESTful API에서는 세션을 사용하지 않으므로 보통 로그아웃이 필요하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안 함. RESTful API는 보통 세션을 사용하지 않고, 토큰 기반의 인증을 사용하여 인증 정보를 전달
                .and()
                .authorizeRequests() // 요청에 대한 접근 권한을 설정
                .antMatchers("/v3/api-docs/**", "/swagger/**", "/swagger-ui", "/swagger-ui/**", "/swagger-resources/**").permitAll() // 인증 없이 접근 가능하도록 허용하는 엔드포인트
                .antMatchers("/api/members/login", "/api/members/signup/**", "/api/dashboard/notification").permitAll() // 인증 없이 접근 가능하도록 허용하는 엔드포인트
                .antMatchers("/employee").hasRole("ADMIN")
                .anyRequest().authenticated(); //  나머지 모든 요청은 인증 필요
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        // 정적인 파일 요청에 대해 무시
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }
}
