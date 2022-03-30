package toy.shop.web.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import toy.shop.entity.ItemImages;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.service.ItemService;
import toy.shop.web.argumentresolver.Login;
import toy.shop.web.dtorequest.ItemCreateDto;
import toy.shop.web.dtoresponse.*;
import toy.shop.service.CategoryService;
import toy.shop.web.filestore.FileStore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

    @GetMapping("/items/new")
    public String getItemForm(Model model, @Login SessionUser sessionUser) {

        List<CategoryResponseDto> result = categoryService.findAllDto();
        ItemCreateDto itemCreateDto = new ItemCreateDto();

        model.addAttribute("item", itemCreateDto);
        model.addAttribute("categories", result);
        model.addAttribute("user",sessionUser);

        return "item-create-form";
    }

    @PostMapping("/items/new")
    public String saveItem(ItemCreateDto itemCreateDto, @Login SessionUser sessionUser) throws IOException {
        List<MultipartFile> file = itemCreateDto.getFile();
        List<ItemImages> itemImages = fileStore.storeFiles(file);


        itemService.addItem(sessionUser.getId(), itemCreateDto.getCategoryId(), itemCreateDto.getItemName(),
                itemCreateDto.getPrice(), itemCreateDto.getQuantity(),itemImages, itemCreateDto.getItemInfo());

        return "redirect:/";
    }


    @ResponseBody
    @GetMapping("/img/{fileName}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    @GetMapping("/item/detail/{itemId}")
    public String getItemDetail(@Login SessionUser sessionUser, @PathVariable Long itemId, Model model) {
        model.addAttribute("user", sessionUser);

        List<CategoryResponseDto> categories = categoryService.findAllDto();
        model.addAttribute("categories", categories);

        DetailItemResponseDto item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "item-detail";
    }



}
