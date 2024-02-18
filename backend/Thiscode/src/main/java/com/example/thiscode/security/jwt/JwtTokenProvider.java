package com.example.thiscode.security.jwt;

import com.example.thiscode.security.common.CommonAuthenticationToken;
import com.example.thiscode.security.model.PrincipalUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String TOKEN = "TOKEN";
    private final String SUBJECT = "SUBJECT";


    // 2주 * 100
    private long tokenTime = 100 * 14 * 24 * 60 * 60 * 1000L;
    private Key key;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode("secretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKey");
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createJwtToken(PrincipalUser principalUser) {
        JwtSubject customJwtSubject = new JwtSubject(principalUser);
        Claims claims = Jwts.claims().setSubject("JWT Token");
        claims.put(SUBJECT, customJwtSubject);

        Date now =new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.debug("Valid Token : {}", claimsJws);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(TOKEN)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        Object subject = claims.get(SUBJECT);
        JwtSubject customJwtSubject = objectMapper.convertValue(subject, JwtSubject.class);

        return new CommonAuthenticationToken(customJwtSubject);
    }

    public JwtSubject getCustomJwtSubject(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        Object subject = claims.get(SUBJECT);
        return objectMapper.convertValue(subject, JwtSubject.class);
    }

}
