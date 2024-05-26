package com.shop.security.oauthprovider;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.shop.user.entity.SocialProvider;
import com.shop.user.entity.User;
import com.shop.security.dto.PrincipalDetail;
import com.shop.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleOauth2Provider implements OAuth2Provider{
	private final UserService userService;

	@Override
	public OAuth2User provideOauth2User(Map<String, Object> attributes) {
		String googleId = attributes.get("sub").toString();
		String email = attributes.get("email").toString();
		User user = userService.findOrCreateUser(googleId, email, SocialProvider.GOOGLE);
		return new PrincipalDetail(user, attributes);
	}

	@Override
	public boolean supports(String provider) {
		return "google".equalsIgnoreCase(provider);
	}
}