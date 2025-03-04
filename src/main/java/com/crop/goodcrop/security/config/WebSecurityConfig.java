package com.crop.goodcrop.security.config;

import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ErrorResponseDto;
import com.crop.goodcrop.security.filter.JwtAuthenticationFilter;
import com.crop.goodcrop.security.filter.JwtAuthorizationFilter;
import com.crop.goodcrop.security.service.UserDetailsServiceImpl;
import com.crop.goodcrop.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // AuthenticationManager 빈 설정 (인증 처리)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // JwtAuthenticationFilter 빈 설정 (로그인 요청 시 사용자 정보 검증 jwt 생성)
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    // JwtAuthorizationFilter 빈 설정 (jwt 유효성 검증, 사용자 인증 상태 설정)
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf 비활성화
                .csrf(csrf -> csrf.disable())
                // jwt 방식 사용하기 위한 설정
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 요청 접근 허가
                .authorizeHttpRequests(auth ->
                        // 회원가입,로그인
                        auth.requestMatchers("/api/auth/**").permitAll()
                                // 단일 상품 조회
                                .requestMatchers("/api/products/{productId}").permitAll()
                                // 상품검색
                                .requestMatchers("/api/v1/products").permitAll()
                                .requestMatchers("/api/v2/products").permitAll()
                                // 인기 검색어
                                .requestMatchers("/api/trends").permitAll()
                                .requestMatchers("/api/v1/trends").permitAll()
                                .requestMatchers("/api/v2/trends").permitAll()
                                // 캐싱처리
                                .requestMatchers("/api/cache/**").permitAll()
                                // 리뷰보기
                                .requestMatchers(HttpMethod.GET, "/api/products/{productId}/reviews").permitAll()
                                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
                )
                // 인증 실패 처리
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
                            // 인증 실패 시 처리할 응답
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                            response.setContentType("application/json;charset=utf-8");

                            String json = objectMapper.writeValueAsString(new ErrorResponseDto(ErrorCode.SECURITY_PERMIT_ERROR, request.getRequestURI()));
                            response.getWriter().write(json);
                        })
                );

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }
}