package com.shop.filestore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.shop.item.entity.ItemImages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;


    public List<ItemImages> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<ItemImages> result = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                ItemImages itemImage = storeFile(multipartFile);
                result.add(itemImage);
            }
        }

        return result;
    }



    public ItemImages storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) return null;

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return new ItemImages(originalFilename,storeFilename);
    }

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    private String createStoreFileName(String originalFilename) {
        int pos = originalFilename.indexOf('.');
        String ext = originalFilename.substring(pos + 1);

        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

}
