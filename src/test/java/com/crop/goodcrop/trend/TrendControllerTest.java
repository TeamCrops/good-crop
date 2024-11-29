package com.crop.goodcrop.trend;

import com.crop.goodcrop.domain.trend.controller.SearchHistoryController;
import com.crop.goodcrop.domain.trend.dto.TopKeywordDto;
import com.crop.goodcrop.domain.trend.service.TopKeywordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TrendControllerTest {
    @InjectMocks
    private SearchHistoryController trendController;

    @Mock
    private TopKeywordService topKeywordService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(trendController).build();
    }

    @Test
    void refreshTopKeyword() throws Exception {
        // given
        doNothing().when(topKeywordService).refreshTopKeyword();

        // when & then
        mockMvc.perform(post("/api/v1/trends"))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveTopKeyword() throws Exception {
        // given
        // Mock 데이터 생성
        List<TopKeywordDto> topKeywords = List.of(
                TopKeywordDto.builder().keyword("고구마").count(30L).build(),
                TopKeywordDto.builder().keyword("감자").count(10L).build()
        );
        when(topKeywordService.retrieveTopKeywordVersion1()).thenReturn(topKeywords);

        // when & then
        mockMvc.perform(get("/api/trends"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(topKeywords.size()))
                .andExpect(jsonPath("$[0].keyword").value(topKeywords.get(0).getKeyword()))
                .andExpect(jsonPath("$[0].count").value(topKeywords.get(0).getCount()))
                .andExpect(jsonPath("$[1].keyword").value(topKeywords.get(1).getKeyword()))
                .andExpect(jsonPath("$[1].count").value(topKeywords.get(1).getCount()));
    }
}
