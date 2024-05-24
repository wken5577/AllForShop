package com.shop.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.entity.UserStatus;
import com.shop.security.dto.PrincipalDetail;

/**
 * 사용자가 로그인 후 username을 등록하지 않은 경우 /register로 이동하도록 하는 필터
 * request url이 /register인 경우에는 필터를 적용하지 않음
 */
public class UserRegisterAuthenticationFilter extends OncePerRequestFilter {
	private static final String REGISTER_URL = "/register";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !request.getRequestURI().equals(REGISTER_URL)){
			PrincipalDetail principal = (PrincipalDetail)authentication.getPrincipal();
			if (principal.getStatus() != UserStatus.REGISTERED){
				response.sendRedirect("/register");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}
