package com.shop.security.service;

import java.util.NoSuchElementException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.security.dto.PrincipalDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).
			orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다 : " + username));
		return new PrincipalDetail(user);
	}
}
