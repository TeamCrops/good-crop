package com.crop.goodcrop.like;

import com.crop.goodcrop.domain.like.controller.LikeController;
import com.crop.goodcrop.domain.like.dto.request.LikeRequestDto;
import com.crop.goodcrop.domain.like.service.LikeService;
import com.crop.goodcrop.security.entity.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {
    @InjectMocks
    private LikeController likeController;

    @Mock
    private LikeService likeService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createLike() throws Exception {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);

        doNothing().when(likeService).createLike(any(UserDetailsImpl.class), any(LikeRequestDto.class), anyLong());

        // when & then
        mockMvc.perform(post("/api/likes/{productId}", productId)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isCreated());
    }

    @Test
    void deleteLike() throws Exception {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        LikeRequestDto requestDto = new LikeRequestDto(memberId);

        doNothing().when(likeService).deleteLike(any(UserDetailsImpl.class), any(LikeRequestDto.class), anyLong());

        // when & then
        mockMvc.perform(delete("/api/likes/{productId}", productId)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isNoContent());
    }
}