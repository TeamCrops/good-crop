package com.crop.goodcrop.domain.trend.repository.h2;

import com.crop.goodcrop.domain.trend.entity.h2.H2SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2SearchHistoryRepository extends JpaRepository<H2SearchHistory, Long> {

}
