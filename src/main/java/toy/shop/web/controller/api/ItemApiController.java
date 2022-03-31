package toy.shop.web.controller.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toy.shop.entity.ItemImages;
import toy.shop.service.CategoryService;
import toy.shop.service.ItemService;
import toy.shop.web.dtorequest.ItemCreateDto;
import toy.shop.web.dtoresponse.AdminItemResponseDto;
import toy.shop.web.dtoresponse.DetailItemResponseDto;
import toy.shop.web.dtoresponse.IndexItemResponseDto;
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
    public Long saveItem(ItemCreateDto itemCreateDto, Long userId) throws IOException {
        List<MultipartFile> file = itemCreateDto.getFile();
        List<ItemImages> itemImages = fileStore.storeFiles(file);


        Long savedItemId = itemService.addItem(userId, itemCreateDto.getCategoryId(), itemCreateDto.getItemName(),
                itemCreateDto.getPrice(), itemCreateDto.getQuantity(), itemImages, itemCreateDto.getItemInfo());

        return savedItemId;
    }

    @GetMapping("/api/item/{itemId}")
    public DetailItemResponseDto getItemDetail(@PathVariable Long itemId) {
        DetailItemResponseDto item = itemService.findById(itemId);

        return item;
    }


    @GetMapping("/api/user/items")
    public List<AdminItemResponseDto> getItemsByUser(Long userId) {
        List<AdminItemResponseDto> items =  itemService.getItemsByUser(userId);
        return items;
    }



    @GetMapping("/api/items")
    public List<IndexItemResponseDto> getItems() {
        List<IndexItemResponseDto> result = itemService.findAll();
        return result;
    }


}
