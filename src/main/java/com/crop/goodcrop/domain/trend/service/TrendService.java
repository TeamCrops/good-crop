package com.crop.goodcrop.domain.trend.service;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.TopKeyword;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.repository.TopKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final TopKeywordRepository topKeywordRepository;

    public List<TopKeywordDto> retrieveTopKeyword() {
        List<TopKeyword> topKeywords = topKeywordRepository.findAll();
        return topKeywords.stream().map(TopKeywordDto::from).toList();
    }

    public void modifyTopKeywordVersion1() {
        List<TopKeywordDto> searchHistories = searchHistoryRepository.findTopFiveOrderBySearchCount();
        topKeywordRepository.deleteAll();
        if(searchHistories == null || searchHistories.isEmpty())
            return;

        List<TopKeyword> topKeywords = new ArrayList<>();
        for(TopKeywordDto topKeywordDto : searchHistories) {
            TopKeyword topKeyword = topKeywordDto.convertDtoToEntity();
            topKeywords.add(topKeyword);
        }
        topKeywordRepository.saveAll(topKeywords);
    }

    public void modifyTopKeywordVersion2() {

    }
}
