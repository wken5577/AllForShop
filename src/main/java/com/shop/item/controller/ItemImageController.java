package com.shop.item.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.shop.common.exception.http.BadRequestException;
import com.shop.filestore.FileStore;
import com.shop.item.entity.Item;
import com.shop.item.repository.ItemRepository;
import com.shop.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemImageController {
	private final ItemRepository itemRepository;
	private final FileStore fileStore;

	/**
	 *
	 * @param filename
	 * @return
	 * @throws MalformedURLException
	 *
	 * <img> 태그의 src 속성에 사용할 수 있는 이미지 파일을 반환.
	 */
	@GetMapping("/item/image/{filename}")
	public Resource getItemImage(@PathVariable String filename) throws MalformedURLException {
		return new UrlResource("file:" + fileStore.getFullPath(filename));
	}

	/**
	 *
 	 * @param itemId
	 * @param filename
	 * @return
	 * @throws MalformedURLException
	 *
	 * image download api, CONTENT_DISPOSITION 헤더를 이용해서 다운로드 파일 명을 기존에 사용자가 업로드할 때
	 * 사용한 파일명으로 설정한다.
	 */
	@GetMapping("/item/image/attach/{itemId}/{filename}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId, @PathVariable String filename) throws MalformedURLException {
		Item item = itemRepository.findById(itemId).orElseThrow(() -> new BadRequestException("해당 상품이 존재하지 않습니다."));
		String uploadFileName = item.getItemImages().stream()
			.filter(itemImages -> itemImages.getStoreFileName().equals(filename))
			.findFirst()
			.orElseThrow(() -> new BadRequestException("해당 파일이 존재하지 않습니다."))
			.getUploadFileName();
		UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(filename));

		String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
			.body(resource);
	}

}
