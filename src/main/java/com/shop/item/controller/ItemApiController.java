package com.shop.item.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.shop.item.entity.ItemImages;
import com.shop.item.service.ItemService;
import com.shop.item.controller.request.ItemCreateDto;
import com.shop.filestore.FileStore;

import java.io.IOException;
import java.util.List;

@RestController
public class ItemApiController {

    private final ItemService itemService;
    private final FileStore fileStore;

    public ItemApiController(ItemService itemService, FileStore fileStore) {
        this.itemService = itemService;
        this.fileStore = fileStore;
    }

    @PostMapping("/api/item/new")
    public Long saveItem(ItemCreateDto itemCreateDto, String username) throws IOException {
        List<MultipartFile> file = itemCreateDto.getFile();
        List<ItemImages> itemImages = fileStore.storeFiles(file);


        Long savedItemId = itemService.addItem(username, itemCreateDto.getCategoryId(), itemCreateDto.getItemName(),
                itemCreateDto.getPrice(), itemImages, itemCreateDto.getItemInfo());

        return savedItemId;
    }



}
