package com.shop.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class UrlMatchChecker {
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public boolean isMatch(String url, String... matches) {
		for (String pattern : matches) {
			if (pathMatcher.match(pattern, url)) {
				return true;
			}
		}
		return false;
	}
}
