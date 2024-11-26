package com.crop.goodcrop.domain.product.dto.response;

import com.crop.goodcrop.domain.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAvgScoreDto {
    @JsonProperty("product")
    private Product product;

    @JsonProperty("avgScore")
    private Double avgScore;
}