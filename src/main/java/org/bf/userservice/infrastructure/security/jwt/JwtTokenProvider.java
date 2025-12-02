package org.bf.userservice.infrastructure.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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

    // JwtTokenProvider.java

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
}
