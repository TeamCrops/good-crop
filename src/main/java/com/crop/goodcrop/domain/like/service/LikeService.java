package com.crop.goodcrop.domain.like.service;

import com.crop.goodcrop.domain.like.dto.request.LikeRequestDto;
import com.crop.goodcrop.domain.like.entity.Like;
import com.crop.goodcrop.domain.like.repository.LikeRepository;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.repository.ProductRepository;
import com.crop.goodcrop.exception.ErrorCode;
import com.crop.goodcrop.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;

    public void createLike(LikeRequestDto requestDto, Long productId) {
        // TODO. khj 시큐리티 완성되면 거기서 뽑아낼 것.
        Member member = Member.builder().id(1L).build();
        Like like = likeRepository.findByProductIdAndMemberId(requestDto.getMemberId(), productId);
        if (like != null)
            throw new ResponseException(ErrorCode.LIKE_DUPLICATE);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseException(ErrorCode.PRODUCT_NOT_FOUND));

        like = requestDto.convertDtoToEntity(member, product);
        likeRepository.save(like);
    }

//    public void removeLike(LikeRequestDto requestDto, Long productId) {
//        // TODO. khj 시큐리티 완성되면 거기서 뽑아낼 것.
//        Member member = Member.builder().id(1L).build();
//        Like like = likeRepository.findByProductIdAndMemberId(requestDto.getMemberId(), productId);
//        if (like == null)
//            throw new ResponseException(ErrorCode.LIKE_NOT_FOUND);
//
//        likeRepository.delete(like);
//    }
}
