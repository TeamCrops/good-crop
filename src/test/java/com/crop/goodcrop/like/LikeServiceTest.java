package com.crop.goodcrop.like;

import com.crop.goodcrop.domain.like.dto.request.LikeRequestDto;
import com.crop.goodcrop.domain.like.entity.Like;
import com.crop.goodcrop.domain.like.repository.LikeRepository;
import com.crop.goodcrop.domain.like.service.LikeService;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
public class LikeServiceTest {
    @Mock
    LikeRepository likeRepository;

    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("좋아요 성공")
    void createLike_success() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);

        when(likeRepository.findByProductIdAndMemberId(memberId, productId)).thenReturn(null);
        when(productRepository.findById(productId)).thenReturn(Optional.of(new Product()));
        when(likeRepository.save(any(Like.class))).thenReturn(new Like());

        // when
        LikeService service = new LikeService(likeRepository, productRepository);
        service.createLike(requestDto, productId);

        // then
        verify(likeRepository, times(1)).findByProductIdAndMemberId(memberId, productId);
        verify(productRepository, times(1)).findById(productId);
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    @DisplayName("좋아요 실패 - 이미 좋아요를 눌렀음")
    void createLike_failure_duplicateLike() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);

        when(likeRepository.findByProductIdAndMemberId(memberId, productId)).thenReturn(new Like());

        // when
        LikeService likeService = new LikeService(likeRepository, productRepository);
        ResponseException exception = assertThrows(ResponseException.class, () -> likeService.createLike(requestDto, productId));

        // then
        verify(likeRepository, times(1)).findByProductIdAndMemberId(memberId, productId);
        assertEquals(
                ErrorCode.LIKE_DUPLICATE.getMessage(),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("좋아요 실패 - 상점이 존재하지 않음")
    void createLike_failure_notFoundProduct() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);

        when(likeRepository.findByProductIdAndMemberId(memberId, productId)).thenReturn(null);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // when
        LikeService likeService = new LikeService(likeRepository, productRepository);
        ResponseException exception = assertThrows(ResponseException.class, () -> likeService.createLike(requestDto, productId));

        // then
        verify(likeRepository, times(1)).findByProductIdAndMemberId(memberId, productId);
        verify(productRepository, times(1)).findById(productId);
        assertEquals(
                ErrorCode.PRODUCT_NOT_FOUND.getMessage(),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("좋아요 취소 성공")
    void deleteLike_success() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);
        Like like = new Like();

        when(likeRepository.findByProductIdAndMemberId(memberId, productId)).thenReturn(like);

        // when
        LikeService service = new LikeService(likeRepository, productRepository);
        service.deleteLike(requestDto, productId);

        // then
        verify(likeRepository, times(1)).findByProductIdAndMemberId(memberId, productId);
        verify(likeRepository, times(1)).delete(like);
    }

    @Test
    @DisplayName("좋아요 취소 실패 - 좋아요를 누르지 않음")
    void deleteLike_failure_notFoundLike() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);

        when(likeRepository.findByProductIdAndMemberId(memberId, productId)).thenReturn(null);

        // when
        LikeService likeService = new LikeService(likeRepository, productRepository);
        ResponseException exception = assertThrows(ResponseException.class, () -> likeService.deleteLike(requestDto, productId));

        // then
        verify(likeRepository, times(1)).findByProductIdAndMemberId(memberId, productId);
        assertEquals(
                ErrorCode.LIKE_NOT_FOUND.getMessage(),
                exception.getMessage()
        );
    }
}
