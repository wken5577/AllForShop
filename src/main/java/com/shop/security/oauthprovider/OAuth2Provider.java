package com.shop.security.oauthprovider;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2Provider {

	OAuth2User provideOauth2User(Map<String, Object> attributes);
	boolean supports(String provider);
}
