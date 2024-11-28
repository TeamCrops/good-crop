package com.crop.goodcrop.domain.trend.repository.mysql;

import com.crop.goodcrop.domain.trend.dto.QTopKeywordDto;
import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.mysql.QSearchHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class SearchHistoryDslRepositoryImpl implements SearchHistoryDslRepository {
    private final JPAQueryFactory queryFactory;
    private final QSearchHistory searchHistory = QSearchHistory.searchHistory;

    @Override
    public List<TopKeywordDto> findTopFiveOrderBySearchCount() {
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusHours(2); // 2시간
        NumberPath<Long> aliasSearchCount = Expressions.numberPath(Long.class, "search_count");
        return queryFactory
                .select(new QTopKeywordDto(
                        searchHistory.keyword,
                        searchHistory.count().as(aliasSearchCount)))
                .from(searchHistory)
                .where(goeStartDate(startDateTime),
                       loeEndDate(endDateTime))
                .groupBy(searchHistory.keyword)
                .orderBy(aliasSearchCount.desc(),
                        searchHistory.createdAt.max().desc())
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
