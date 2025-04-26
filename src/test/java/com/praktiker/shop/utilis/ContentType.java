package com.praktiker.shop.utilis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContentType {
    JSON("application/json");

    private final String name;
}
