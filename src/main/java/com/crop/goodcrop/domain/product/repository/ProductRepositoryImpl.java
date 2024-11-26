package com.crop.goodcrop.domain.product.repository;

import com.crop.goodcrop.domain.like.entity.QLike;
import com.crop.goodcrop.domain.product.entity.Product;
import com.crop.goodcrop.domain.product.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryQuery {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<Page<Product>> searchProductsWithFilters(
            String keyword,
            int minPrice,
            boolean isTrend,
            Pageable pageable
    ) {
        QProduct qProduct = QProduct.product;
        QLike qLike = QLike.like;

        // WHERE 절
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(qProduct.name.containsIgnoreCase(keyword));
        if (minPrice > 0) {
            whereClause.and(qProduct.price.goe(minPrice));
        }

        JPAQuery<Product> query = queryFactory
                .selectFrom(qProduct)
                .leftJoin(qLike)
                .on(qProduct.id.eq(qLike.id))
                .where(whereClause);

        // 1) isTrend == true -> 기본정렬은 좋아요 수 내림차순 정렬
        // 2) 가격 오름차순 정렬 / 키워드 오름차순 정렬
        if (isTrend) {
            query.groupBy(qProduct.id)
                    .orderBy(qLike.count().desc());
        }

        if (minPrice > 0) query.orderBy(qProduct.price.asc());
        else query.orderBy(qProduct.name.asc());

        // Pageable 적용
        List<Product> products = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 페이지네이션을 위한 전체 데이터 수
        Long totalCount = queryFactory
                .select(qProduct.count())
                .from(qProduct)
                .leftJoin(qLike)
                .on(qProduct.id.eq(qLike.id))
                .where(whereClause)
                .fetchOne();
        totalCount = totalCount == null ? 0 : totalCount;


        return Optional.ofNullable(
                products.isEmpty() ? null : new PageImpl<>(products, pageable, totalCount)
        );
    }

}
