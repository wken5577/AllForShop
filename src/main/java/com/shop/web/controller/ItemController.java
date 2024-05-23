package com.shop.web.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shop.entity.ItemImages;
import com.shop.entity.User;
import com.shop.security.dto.PrincipalDetail;
import com.shop.service.CategoryService;
import com.shop.service.ItemService;
import com.shop.web.dto.dtorequest.ItemCreateDto;
import com.shop.web.dto.dtoresponse.CategoryResponseDto;
import com.shop.web.dto.dtoresponse.ItemResponseDto;
import com.shop.web.filestore.FileStore;

@Controller
public class ItemController {

	private final CategoryService categoryService;
	private final ItemService itemService;
	private final FileStore fileStore;

	public ItemController(CategoryService categoryService, ItemService itemService, FileStore fileStore) {
		this.categoryService = categoryService;
		this.itemService = itemService;
		this.fileStore = fileStore;
	}

	@GetMapping("/item/new")
	public String getItemForm(Model model) {

		List<CategoryResponseDto> result = categoryService.findAllDto();
		ItemCreateDto itemCreateDto = new ItemCreateDto();

		model.addAttribute("item", itemCreateDto);
		model.addAttribute("categories", result);

		return "item/item-create-form";
	}

	@PostMapping("/item/new")
	public String saveItem(ItemCreateDto itemCreateDto, @AuthenticationPrincipal PrincipalDetail principalDetail) throws
		IOException {
		List<MultipartFile> file = itemCreateDto.getFile();
		List<ItemImages> itemImages = fileStore.storeFiles(file);
		User user = principalDetail.getUser();

		itemService.addItem(user.getUsername(), itemCreateDto.getCategoryId(), itemCreateDto.getItemName(),
			itemCreateDto.getPrice(), itemImages, itemCreateDto.getItemInfo());

		return "redirect:/";
	}

	@GetMapping("/item/{itemId}")
	public String getItemDetail(@PathVariable Long itemId, Model model) {

		ItemResponseDto item = itemService.findById(itemId);
		model.addAttribute("item", item);
		return "item/item-detail";
	}

	@ResponseBody
	@GetMapping("/img/{fileName}")
	public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
		return new UrlResource("file:" + fileStore.getFullPath(fileName));
	}

}
