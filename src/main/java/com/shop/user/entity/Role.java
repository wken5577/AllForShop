package com.shop.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER ("ROLE_USER","일반유저");

    private final String key;
    private final String value;
}
