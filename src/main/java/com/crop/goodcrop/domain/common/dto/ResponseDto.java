package com.crop.goodcrop.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private T data;

    public static <T> ResponseDto<T> of(T data) {
        return ResponseDto.<T>builder()
                .data(data)
                .build();
    }
}
