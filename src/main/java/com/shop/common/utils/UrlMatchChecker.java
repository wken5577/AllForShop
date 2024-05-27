package com.shop.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

public class UrlMatchChecker {
	private static final AntPathMatcher pathMatcher = new AntPathMatcher();

	public static boolean isMatch(String url, String... matches) {
		for (String pattern : matches) {
			if (pathMatcher.match(pattern, url)) {
				return true;
			}
		}
		return false;
	}
}
