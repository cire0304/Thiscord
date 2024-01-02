package com.example.thiscode.security.jwt;

import com.example.thiscode.security.authentication.model.PrincipalUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final String TOKEN = "TOKEN";

    // 2주 * 100
    private long tokenTime = 100 * 14 * 24 * 60 * 60 * 1000L;
    private Key key;


    // TODO: 나중에 수정
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode("secretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKeysecretdasdasdasdasdaKey");
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // TODO: 인자는 다시 생각해보자
    public String createToken(String userId, String nickname, String usercode, String email, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("usercode", usercode);
        claims.put("nickname", nickname);
        claims.put("email", email);
        claims.put("authorities", authorities);

        Date now =new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createToken(PrincipalUser principalUser) {
        // TODO: subject는 userId로 변경
        // TODO: 아래의 인자는 나중에 다시 변경해야함
        Claims claims = Jwts.claims().setSubject(principalUser.getUsername());
        claims.put("usercode", principalUser.getUsername());
        claims.put("nickname", principalUser.getNickname());
        claims.put("email", principalUser.getEmail());
        claims.put("authorities", principalUser.getAuthorities());

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
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(TOKEN)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    // TODO: JwtAuthenticationToken.principal에 넣을 객체를 만들어야함(nickname, usercode, email)
    // TODO: 권한은 생각하지 말자
    public Authentication getAuthentication(String token) {
        Claims body = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String userId = body.getSubject();
        return new JwtAuthenticationToken(userId, token);
    }
}
