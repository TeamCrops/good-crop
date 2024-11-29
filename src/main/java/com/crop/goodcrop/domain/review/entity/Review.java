package com.crop.goodcrop.domain.review.entity;

import com.crop.goodcrop.domain.common.entity.Timestamped;
import com.crop.goodcrop.domain.member.entity.Member;
import com.crop.goodcrop.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @Column(nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Review (String comment, int score, Member member, Product product) {
        this.comment = comment;
        this.score = score;
        this.member = member;
        this.product = product;
    }

    public static Review of(String comment, int score, Member member, Product product) {
        return new Review(comment, score, member, product);
    }

    public void modify(String comment, int score) {
        this.comment = comment;
        this.score = score;
    }
}
