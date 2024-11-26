package com.crop.goodcrop.security.util;

import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long userId, String email, String nickname) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(userId))
                        .claim("email", email)
                        .claim("nickname", nickname)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring("Bearer ".length());
        }
        throw new ResponseException(ErrorCode.TOKEN_MISSING);
    }

    public Claims parseToken(String tokenValue) {
        try {
            // 토큰에서 Claims를 추출
            return Jwts.parser()
                    .setSigningKey(key)  // 암호화 키 설정
                    .build()
                    .parseClaimsJws(tokenValue)  // JWT 토큰 파싱
                    .getBody();  // claims 반환
        } catch (ExpiredJwtException e) {
            // 토큰 만료 예외 처리
            throw new ResponseException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            // JWT 형식이 지원되지 않을 때 예외 처리
            throw new ResponseException(ErrorCode.INVALID_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            // 잘못된 JWT 형식에 대한 예외 처리
            throw new ResponseException(ErrorCode.INVALID_TOKEN_FORMAT);
        } catch (SignatureException e) {
            // 서명 검증 실패 예외 처리
            throw new ResponseException(ErrorCode.INVALID_TOKEN_SIGNATURE);
        } catch (Exception e) {
            // 일반적인 예외 처리 (파서오류)
            throw new ResponseException(ErrorCode.INVALID_TOKEN_PARSING);
        }
    }

    public boolean verifyAccessToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
