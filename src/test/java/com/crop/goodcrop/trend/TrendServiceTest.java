package com.crop.goodcrop.trend;

import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.entity.TopKeyword;
import com.crop.goodcrop.domain.trend.repository.SearchHistoryRepository;
import com.crop.goodcrop.domain.trend.repository.TopKeywordRepository;
import com.crop.goodcrop.domain.trend.service.TopKeywordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
public class TrendServiceTest {
    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    TopKeywordRepository topKeywordRepository;

    @Mock
    SearchHistoryRepository searchHistoryRepository;

    @Test
    @DisplayName("인기 검색어 갱신 성공")
    void modifyTopKeyword_Version1_success() {
        // given
        List<TopKeywordDto> searchHistories = List.of(new TopKeywordDto());
        List<TopKeyword> topKeywords = List.of(new TopKeyword());
        when(searchHistoryRepository.findTopFiveOrderBySearchCount()).thenReturn(searchHistories);
        when(topKeywordRepository.saveAll(anyList())).thenReturn(topKeywords);

        // when
        TopKeywordService service = new TopKeywordService(redisTemplate, searchHistoryRepository, topKeywordRepository);
        service.refreshTopKeyword();

        // then
        verify(searchHistoryRepository, times(1)).findTopFiveOrderBySearchCount();
        verify(topKeywordRepository, times(1)).deleteAll();
        verify(topKeywordRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("인기 검색어 조회 성공")
    void retrieveTopKeyword_success() {
        // given
        List<TopKeyword> topKeywords = List.of(TopKeyword.builder().keyword("고구마").count(30L).build());
        when(topKeywordRepository.findAll()).thenReturn(topKeywords);

        // when
        TopKeywordService service = new TopKeywordService(redisTemplate, searchHistoryRepository, topKeywordRepository);
        List<TopKeywordDto> results = service.retrieveTopKeywordVersion1();

        // then
        verify(topKeywordRepository, times(1)).findAll();
        assertEquals(topKeywords.size(), results.size());
        assertEquals(topKeywords.get(0).getKeyword(), results.get(0).getKeyword());
        assertEquals(topKeywords.get(0).getCount(), results.get(0).getCount());
    }
}
