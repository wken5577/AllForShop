package com.shop.security.oauth;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.shop.entity.SocialProvider;
import com.shop.entity.User;
import com.shop.security.dto.PrincipalDetail;
import com.shop.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverOauth2Provider implements OAuth2Provider{

	private final UserService userService;

	@Override
	public OAuth2User provideOauth2User(Map<String, Object> attributes) {
		Object response = attributes.get("response");
		if (response instanceof Map) {
			Map<String, Object> responseMap = (Map<String, Object>) response;
			String email = responseMap.get("email").toString();
			String naverId = responseMap.get("id").toString();
			User user = userService.findOrCreateUser(naverId, email, SocialProvider.NAVER);
			return new PrincipalDetail(user, attributes);
		}
		throw new IllegalArgumentException("잘못된 네이버 사용자 정보입니다.");
	}

	@Override
	public boolean supports(String provider) {
		return "naver".equalsIgnoreCase(provider);
	}
}
