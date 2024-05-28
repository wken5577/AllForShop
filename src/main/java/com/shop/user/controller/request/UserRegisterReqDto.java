package com.shop.user.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterReqDto {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String username;
}
