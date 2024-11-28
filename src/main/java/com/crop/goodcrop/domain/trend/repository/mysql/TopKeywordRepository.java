package com.crop.goodcrop.domain.trend.repository.mysql;

import com.crop.goodcrop.domain.trend.entity.mysql.TopKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopKeywordRepository extends JpaRepository<TopKeyword, Long> {
}
