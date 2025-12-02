package org.bf.userservice.infrastructure.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String key, @Value("${jwt.access-token-expiration}") long accessTokenExpiration, @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    /**
     * Access Token을 생성
     * 토큰에는 userId, username, email, roles 등 사용자 상세 정보가 Claims로 포함
     */
    public String createAccessToken(Long userId, String username, String email, List<String> roles) {

        Instant nowInstant = Instant.now();
        Instant expirationInstant = nowInstant.plusMillis(accessTokenExpiration);

        Date issuedAtDate = Date.from(nowInstant);
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .claim("email", email)
                .claim("roles", roles)
                .issuedAt(issuedAtDate)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    /**
     * Refresh Token을 생성
     * 토큰에는 userId만 포함, 긴 만료 시간
     */
    public String createRefreshToken(Long userId) {

        Instant nowInstant = Instant.now();
        Instant expirationInstant = nowInstant.plusMillis(refreshTokenExpiration);

        Date issuedAtDate = Date.from(nowInstant);
        Date expirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(issuedAtDate)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    /**
     * Refresh Token의 유효성 (서명 및 만료 시간)을 검증
     */
    public boolean validateToken(String token) {
        try {
            // Jwts.parserBuilder()를 통해 서명 키를 설정하고 토큰을 파싱 시도
            Jwts.parser().verifyWith(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 잘못된 JWT 서명 (변조)
            log.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // 토큰 만료
            log.warn("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 형식
            log.warn("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // 잘못된 인자 (토큰이 null이거나 비어있음)
            log.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public Long getUserIdFromToken(String token) {

        // 토큰에서 Claims를 추출
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // "userId" 클레임을 Long 타입으로 추출
        return claims.get("userId", Long.class);
    }
}
