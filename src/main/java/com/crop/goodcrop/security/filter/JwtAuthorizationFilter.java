package com.crop.goodcrop.security.filter;

import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ErrorResponseDto;
import com.crop.goodcrop.security.service.UserDetailsServiceImpl;
import com.crop.goodcrop.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 후 토큰 검증 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(final JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI();

        // 허용된 URL
        if (url.startsWith("/api/auth") ||
                url.startsWith("/api/products") ||
                url.startsWith("/api/v1/products") ||
                url.matches("/api/products/\\d+/reviews")) {

            chain.doFilter(request, response);
            return;
        }
        log.info("현재 url = {}", url);

        // JWT 검증 로직 수행
        String bearerJwt = httpRequest.getHeader("Authorization");

        // 토큰 없음
        if (bearerJwt == null) {
            sendErrorResponse(httpResponse, ErrorCode.TOKEN_MISSING,request);
            return;
        }

        String jwt = jwtUtil.substringToken(bearerJwt);
        log.info("토큰 = {}", jwt);

        // 유효하지 않은 토큰
        if (!jwtUtil.verifyAccessToken(jwt)) {
            sendErrorResponse(httpResponse, ErrorCode.INVALID_TOKEN_SIGNATURE,request);
            return;
        }

        try {
            // JWT 유효성 검사와 claims 추출
            Claims claims = jwtUtil.parseToken(jwt);

            log.info("JWT Claims: {}", claims);

            //잘못된 토큰
            if (claims == null) {
                sendErrorResponse(httpResponse, ErrorCode.INVALID_TOKEN_FORMAT,request);
                return;
            }

            setAuthentication((String) claims.get("email"));
            chain.doFilter(request, response);

        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            sendErrorResponse(httpResponse, ErrorCode.INVALID_TOKEN_SIGNATURE,request);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            sendErrorResponse(httpResponse, ErrorCode.EXPIRED_TOKEN,request);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            sendErrorResponse(httpResponse, ErrorCode.INVALID_TOKEN_TYPE,request);
        } catch (Exception e) {
            log.error("Internal server error", e);
            sendErrorResponse(httpResponse, ErrorCode.INTERNAL_SERVER_ERROR,request);
        }
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        log.info("인증처리완료");
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        log.info("객체생성" + userDetails);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode, HttpServletRequest request) throws IOException {
        // JSON 형태로 응답 생성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());

        String json = objectMapper.writeValueAsString(new ErrorResponseDto(errorCode,request.getRequestURI()));
        response.getWriter().write(json);
    }

}
