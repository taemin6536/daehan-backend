package com.daehan.back.common.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30분
    private final Key key;
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        log.info("JwtTokenProvider 생성자 호출 => secretKey : {}", secretKey);

        // Base64 디코딩
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 bytes for HS256");
        }

        // Base64 디코딩된 keyBytes를 사용해야 한다!
        this.key = Keys.hmacShaKeyFor(keyBytes);

        log.info("Decoded jwt.secret (Base64 -> Bytes): {}", Base64.getEncoder().encodeToString(keyBytes));
    }

    public String createToken(String email, Collection<? extends GrantedAuthority> authorities) {
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String authority = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, authority)
                .signWith(key, SignatureAlgorithm.HS256)  // 명시적으로 key 객체 사용
                .setExpiration(accessTokenExpiresIn)
                .compact();
    }
    //토큰에서 인증정보 가져오기
    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        //클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
    //토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            log.info("Validating token with jwt.secret...");
            log.info("Current JWT SecretKey: {}", Base64.getEncoder().encodeToString(key.getEncoded()));
            // 명시적으로 SignatureAlgorithm 지정
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            log.info("Token validated successfully. Claims: {}", claimsJws.getBody());

            return true;
        } catch (Exception e) {
            log.error("토큰 검증 실패 상세 정보:", e);
            return false;
        }
    }
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getUserNameFromJwt(String token){
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }
}
