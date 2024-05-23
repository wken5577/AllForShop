package com.shop.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SocialProvider {
        GOOGLE("GOOGLE", "google login"),
        NAVER("NAVER", "naver login"),
        NONE("NONE", "username password login");

        private final String key;
        private final String displayName;
}
