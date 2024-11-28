package com.crop.goodcrop.domain.trend.repository.h2;

import com.crop.goodcrop.config.H2Config;
import com.crop.goodcrop.domain.trend.dto.QTopKeywordDto;
import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.h2.QH2SearchHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class H2SearchHistoryDslRepositoryImpl implements H2SearchHistoryDslRepository {
    private final JPAQueryFactory queryFactory;
    private final QH2SearchHistory h2SearchHistory = QH2SearchHistory.h2SearchHistory;

    @Override
    public List<TopKeywordDto> findTopKeywordOrderBySearchCount() {
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusHours(2); // 2시간
        NumberPath<Long> aliasSearchCount = Expressions.numberPath(Long.class, "search_count");
        return queryFactory
                .select(new QTopKeywordDto(
                        h2SearchHistory.keyword,
                        h2SearchHistory.count().as(aliasSearchCount)))
                .from(h2SearchHistory)
                .where(goeStartDate(startDateTime),
                       loeEndDate(endDateTime))
                .groupBy(h2SearchHistory.keyword)
                .orderBy(aliasSearchCount.desc(),
                        h2SearchHistory.createdAt.max().desc())
                .limit(10)
                .fetch();
    }

    private BooleanExpression goeStartDate(LocalDateTime startDate) {
        if (startDate == null)
            return null;
        return h2SearchHistory.createdAt.goe(startDate);
    }

    private BooleanExpression loeEndDate(LocalDateTime endDate) {
        if (endDate == null)
            return null;
        return h2SearchHistory.createdAt.loe(endDate);
    }
}
