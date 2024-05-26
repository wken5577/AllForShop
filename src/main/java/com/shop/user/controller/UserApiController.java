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
public class UserApiController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity join(@RequestBody @Validated UserJoinReqDto userDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity(new UserErrorDto(bindingResult.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}

		try {
			Long id = userService.save(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
			return new ResponseEntity(id, HttpStatus.OK);

		} catch (IllegalStateException e) {
			return new ResponseEntity(new UserErrorDto("username", e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody UserRegisterReqDto reqDto,
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail) {
		userService.register(reqDto.getUsername(), principalDetail.getUser().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
