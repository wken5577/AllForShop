package com.shop.item.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateReqDto {

	@NotBlank(message = "상품명을 입력해주세요.")
	@Schema(description = "상품명")
	private String itemName;

	@NotNull(message = "카테고리를 선택해주세요.")
	@Schema(description = "카테고리 ID")
	private Long categoryId;

	@Positive(message = "가격은 0보다 커야합니다.")
	@Schema(description = "가격")
	private int price;

	@NotBlank(message = "상품 정보를 입력해주세요.")
	@Schema(description = "상품 정보")
	private String itemInfo;
}