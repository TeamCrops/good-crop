package com.crop.goodcrop.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {

    SEARCH_CACHE("searchCache", 24, 10000);

    private final String cacheName;
    private final int expiredAfterWrite;
    private final int maximumSize;
}
