package com.shop.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.shop.security.oauth.OAuth2Provider;

@RequiredArgsConstructor
@Service
public class Oauth2PrincipalDetailService extends DefaultOAuth2UserService {
	private final List<OAuth2Provider> oAuth2Providers;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		Map<String, Object> attributes = oauth2User.getAttributes();

		OAuth2Provider provider = oAuth2Providers.stream()
			.filter(p -> p.supports(userRequest.getClientRegistration().getRegistrationId()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth2 공급자입니다."));

		return provider.provideOauth2User(attributes);
	}
}
