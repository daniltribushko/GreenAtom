package com.example.GreenAtom.services.imp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * @author Tribushko Danil
 * Класс для работы с токеном jwt
 */
@Service
public class JwtTokenService {
    @Value("${token.secret}")
    private String secret;

    /**
     * Генерация токена
     *
     * @param userDetails пользователь
     * @return jwt токен
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails
                .getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
        claims.put("roles", roles);

        Date issueDate = new Date();
        Date expiredDate = new Date(issueDate.getTime() + 1000 * 3600 * 24);
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(issueDate)
                .expiration(expiredDate)
                .signWith(getKey())
                .compact();
    }

    /**
     * Получение имени пользователя из токена
     *
     * @param token jwt токен
     * @return имя пользователя
     */
    public String getUserName(String token) {
        return getAllClaim(token)
                .getSubject();
    }

    /**
     * Проверка токена на валидацию
     *
     * @param token       jwt токен
     * @param userDetails пользолватель
     * @return булевское значение валидности токена
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userName = getUserName(token);
        return Objects.equals(userName, userDetails.getUsername()) &&
                getAllClaim(token)
                        .getExpiration()
                        .after(new Date());
    }

    private Claims getAllClaim(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Получение секретной подписи
     *
     * @return секретная подпись
     */
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
