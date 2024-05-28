package com.shop.item.controller.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCategoryReqDto {

	@NotBlank(message = "카테고리명을 입력해주세요.")
	private String categoryName;
}
