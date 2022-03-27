package toy.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import toy.shop.entity.ItemImages;
import toy.shop.oauth.dto.SessionUser;
import toy.shop.service.ItemService;
import toy.shop.web.dtorequest.ItemCreateDto;
import toy.shop.web.dtoresponse.*;
import toy.shop.entity.Item;
import toy.shop.service.CategoryService;
import toy.shop.web.filestore.FileStore;

import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    @GetMapping("/upload")
    public String newFile(Model model) {
        List<CategoryResponseDto> result = categoryService.findAllDto();
        model.addAttribute("categories", result);
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveItem(ItemCreateDto itemCreateDto, HttpSession session) throws IOException {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        List<MultipartFile> file = itemCreateDto.getFile();
        List<ItemImages> itemImages = fileStore.storeFiles(file);


        itemService.addItem(sessionUser.getId(), itemCreateDto.getCategoryId(), itemCreateDto.getItemName(),
                itemCreateDto.getPrice(), itemCreateDto.getQuantity(),itemImages);

        return "redirect:/";
    }


}
