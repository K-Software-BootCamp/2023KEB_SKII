package com.sk2.smartfactory_bearingrul.util.jwt;

import com.sk2.smartfactory_bearingrul.dto.LoginMemberDto;
import com.sk2.smartfactory_bearingrul.service.LoginMemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long tokenValidTime = 30 * 60 * 1000L; // 토큰 유효시간 = 30분
    private final LoginMemberService loginMemberService;

    // 객체 초기화, SecretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    // 토큰 생성
    public String createToken(String memberId, String position) {
        return Jwts.builder()
                .setClaims(createClaims(memberId, position)) // 정보 저장
                .setIssuedAt(new Date()) // 토큰 발행시간
                .setExpiration(new Date(new Date().getTime() + tokenValidTime)) // 토큰 유효시간
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // 암호화 알고리즘, secret 값
                .compact();
    }

    // payload
    private Map<String, Object> createClaims(String memberId, String position) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberId", memberId);
        claims.put("position", position);
        return claims;
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        //Spring Security에서 제공하는 메서드 override해서 사용해야 함
        LoginMemberDto loginMemberDto = loginMemberService.loadUserByUsername(this.getMemberId(token));
        return new UsernamePasswordAuthenticationToken(loginMemberDto.toEntity(), "", loginMemberDto.getAuthorities());
    }

    // 토큰에서 Member 정보 추출
    public String getMemberId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.get("memberId", String.class);
        } catch (JwtException e) {
            throw new IllegalArgumentException("토큰으로부터 멤버를 찾을 수 없습니다.");
        }
    }

    // 토큰 유효성, 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
}