package com.crop.goodcrop.domain.trend.repository.mysql;

import com.crop.goodcrop.domain.trend.entity.mysql.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long>, SearchHistoryDslRepository {
}
