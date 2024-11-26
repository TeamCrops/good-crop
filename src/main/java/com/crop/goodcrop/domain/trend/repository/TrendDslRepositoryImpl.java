package com.crop.goodcrop.domain.trend.repository;

import com.crop.goodcrop.domain.trend.dto.QTrendResponseDto;
import com.crop.goodcrop.domain.trend.dto.TrendResponseDto;
import com.crop.goodcrop.domain.trend.entity.QSearchHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TrendDslRepositoryImpl implements TrendDslRepository {
    private final JPAQueryFactory queryFactory;
    private final QSearchHistory searchHistory = QSearchHistory.searchHistory;

    @Override
    public List<TrendResponseDto> findTopFiveOrderBySearchCount() {
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusHours(2); // 2시간

        NumberPath<Long> aliasSearchCount = Expressions.numberPath(Long.class, "search_count");
        DatePath<LocalDateTime> aliasLastedAt = Expressions.datePath(LocalDateTime.class, "lasted_at");

        return queryFactory
                .select(new QTrendResponseDto(
                        searchHistory.keyword,
                        searchHistory.count().as(aliasSearchCount),
                        searchHistory.createdAt.max().as(aliasLastedAt)))
                .from(searchHistory)
                .where(goeStartDate(startDateTime),
                        loeEndDate(endDateTime))
                .groupBy(searchHistory.keyword)
                .orderBy(aliasSearchCount.desc(),
                        aliasLastedAt.desc())
                .limit(5)
                .fetch();
    }

    private BooleanExpression goeStartDate(LocalDateTime startDate) {
        if (startDate == null)
            return null;
        return searchHistory.createdAt.goe(startDate);
    }

    private BooleanExpression loeEndDate(LocalDateTime endDate) {
        if (endDate == null)
            return null;
        return searchHistory.createdAt.loe(endDate);
    }
}
