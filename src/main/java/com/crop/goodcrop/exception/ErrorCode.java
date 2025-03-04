package com.crop.goodcrop.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 응답 시 사용되는 상태 코드와 메시지를 정의하는 enum
 *
 * @see <a href="https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C">HTTP 상태 코드</a>
 * @since 2024-11-25
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth
    EXPIRED_TOKEN("A000", "만료된 토큰입니다",HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_SIGNATURE("A001", "유효하지 않은 토큰 서명 입니다",HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_TYPE("A002", "유효하지 않은 JWT 형식입니다", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_FORMAT("A003", "잘못된 JWT 토큰입니다", HttpStatus.BAD_REQUEST),
    MISSING_BEARER_TOKEN("A004", "Bearer 인증 정보가 올바르지 않습니다", HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS("A005", "이미 사용 중인 이메일입니다", HttpStatus.CONFLICT),
    TOKEN_MISSING("A006", "Authorization 헤더가 누락되었습니다", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN_PARSING("A007", "JWT 토큰 파싱 중 오류가 발생했습니다", HttpStatus.BAD_REQUEST),

    // Server(S)
    INTERNAL_SERVER_ERROR("S000","서버 내부 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    SECURITY_PERMIT_ERROR("S001","Security 인증 오류", HttpStatus.UNAUTHORIZED),

    // Common(C)
    UNKNOWN_ERROR("C000","알 수 없는 에러", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_INPUT("C001", "잘못된 값 입력", HttpStatus.BAD_REQUEST),

    // User(U)
    USER_NOT_FOUND("U000", "존재하지 않는 유저입니다", HttpStatus.NOT_FOUND),
    ALREADY_USER_EXIST("U001", "존재하는 사용자입니다.", HttpStatus.ALREADY_REPORTED),
    USER_UPDATE_FORBIDDEN("U002", "프로필을 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN),
    PASSWORD_NOT_MATCH("U003", "비밀번호가 다릅니다.", HttpStatus.BAD_REQUEST),
    USER_RETRIEVE_FORBIDDEN("U004", "프로필을 조회할 권한이 없습니다", HttpStatus.FORBIDDEN),
    USER_DELETE_FORBIDDEN("U005","회원 탈퇴할 권한이 없습니다", HttpStatus.FORBIDDEN),

    // Product
    PRODUCT_NOT_FOUND("P000", "존재하지 않는 상품입니다", HttpStatus.NOT_FOUND),
    PRODUCT_SEARCH_NOT_FOUND("P001", "검색 조건에 맞는 상품을 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    // Like
    LIKE_NOT_FOUND("L000", "해당 상품에 좋아요를 누르지 않았습니다.", HttpStatus.NOT_FOUND),
    LIKE_DUPLICATE("L001", "이미 좋아요를 누르셨습니다.", HttpStatus.BAD_REQUEST),

    // Review
    REVIEW_NOT_FOUND("R000", "존재하지 않는 리뷰입니다", HttpStatus.NOT_FOUND),
    REVIEW_NO_PERMISSION_TO_EDIT("R001", "리뷰를 수정할 권한이 없습니다", HttpStatus.FORBIDDEN),
    REVIEW_NOT_OWNED("R002", "해당 가게의 리뷰가 아닙니니다", HttpStatus.FORBIDDEN);

    private final String errCode;
    private final String message;
    private final HttpStatus httpStatus;
}
