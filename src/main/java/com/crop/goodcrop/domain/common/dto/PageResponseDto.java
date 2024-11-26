package com.crop.goodcrop.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> data;
    private int page;
    private int size;
    private int totalPage;

    public static <T> PageResponseDto<T> of(List<T> data, Pageable pageable, int totalPage) {
        return PageResponseDto.<T>builder()
                .data(data)
                .page(pageable.getPageNumber() + 1)
                .size(pageable.getPageSize())
                .totalPage(totalPage)
                .build();
    }
}
