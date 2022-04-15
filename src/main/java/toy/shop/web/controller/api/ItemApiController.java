package toy.shop.web.controller.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toy.shop.entity.ItemImages;
import toy.shop.service.CategoryService;
import toy.shop.service.ItemService;
import toy.shop.web.dto.dtorequest.ItemCreateDto;
import toy.shop.web.filestore.FileStore;

import java.io.IOException;
import java.util.List;

@RestController
public class ItemApiController {

    private final CategoryService categoryService;
    private final ItemService itemService;
    private final FileStore fileStore;

    public ItemApiController(CategoryService categoryService, ItemService itemService, FileStore fileStore) {
        this.categoryService = categoryService;
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
