package com.shop.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.security.dto.PrincipalDetail;
import com.shop.user.service.UserService;
import com.shop.user.controller.request.UserJoinReqDto;
import com.shop.user.controller.request.UserRegisterReqDto;
import com.shop.user.controller.response.UserErrorDto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<Void> join(@RequestBody @Validated UserJoinReqDto userDto) {
		userService.save(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Validated UserRegisterReqDto reqDto,
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail) {
		userService.register(reqDto.getUsername(), principalDetail.getUserId());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
