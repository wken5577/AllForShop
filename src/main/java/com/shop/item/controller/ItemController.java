package com.shop.item.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.common.dto.PageRequestDto;
import com.shop.common.validator.MultipartFileValidator;
import com.shop.filestore.FileStore;
import com.shop.item.controller.request.ItemCreateReqDto;
import com.shop.item.controller.response.ItemDetailRespDto;
import com.shop.item.controller.response.ItemListRespDto;
import com.shop.item.entity.Item;
import com.shop.item.entity.ItemImages;
import com.shop.item.service.ItemService;
import com.shop.security.dto.PrincipalDetail;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	private final FileStore fileStore;
	private final MultipartFileValidator multipartFileValidator;

	@PostMapping(value = "/item", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> saveItem(
		@Validated @RequestPart("itemCreateData") ItemCreateReqDto itemCreateDto,
		@RequestPart("file") List<MultipartFile> file,
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail,
		BindingResult bindingResult
	) throws IOException, BindException {
		if (bindingResult.hasErrors() || !multipartFileValidator.validate(file, bindingResult)) {
			throw new BindException(bindingResult);
		}
		List<ItemImages> itemImages = fileStore.storeFiles(file);

		itemService.addItem(principalDetail.getUserId(), itemCreateDto.getCategoryId(), itemCreateDto.getItemName(),
			itemCreateDto.getPrice(), itemImages, itemCreateDto.getItemInfo());

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/item/{itemId}")
	public ResponseEntity<Void> deleteItem(
		@Parameter(hidden = true) @AuthenticationPrincipal PrincipalDetail principalDetail,
		@PathVariable Long itemId
	) {
		itemService.deleteItem(principalDetail.getUserId(), itemId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/item/{itemId}")
	public ResponseEntity<ItemDetailRespDto> getItemDetail(@PathVariable Long itemId) {
		ItemDetailRespDto itemDetailRespDto = itemService.getItemDetail(itemId);
		return ResponseEntity.ok(itemDetailRespDto);
	}

	@GetMapping("/items")
	public ResponseEntity<ItemListRespDto> getItems(@RequestParam(required = false) String keyword, @RequestParam(required = false) Long categoryId,
		@Validated PageRequestDto pageRequestDto) {
		Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize());
		// Page<Item> items = itemService.getItems(keyword, categoryId, pageable);
		return ResponseEntity.ok(itemService.getItems(keyword, categoryId, pageable));
	}
}
