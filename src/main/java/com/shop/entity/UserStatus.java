package com.shop.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {

	AUTHORIZED("AUTHORIZED", "인증됨"),
	REGISTERED("REGISTERED", "가입됨");

	private final String key;
	private final String displayName;
}
