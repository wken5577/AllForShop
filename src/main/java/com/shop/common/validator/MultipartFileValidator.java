package com.shop.common.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.shop.common.config.properties.FileUploadProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MultipartFileValidator {

	private final FileUploadProperties fileUploadProperties;

	public boolean validate(List<MultipartFile> files, BindingResult bindingResult) {
		if (files == null || files.isEmpty()) {
			bindingResult.reject("files", "No files uploaded");
			return false;
		}

		if (files.size() > fileUploadProperties.getMaxFileCount()) {
			bindingResult.reject("files",
				"Too many files. Maximum allowed is " + fileUploadProperties.getMaxFileCount());
			return false;
		}

		for (MultipartFile file : files) {
			if (file.getSize() > fileUploadProperties.getMaxFileSize()) {
				bindingResult.reject("file",
					"File size is too large. Maximum allowed is " + (fileUploadProperties.getMaxFileSize() / (1024
						* 1024)) + " MB");
				return false;
			}

			if (!fileUploadProperties.getAllowedFileTypes().contains(file.getContentType())) {
				bindingResult.reject("file",
					"Invalid file type. Allowed types are: " + fileUploadProperties.getAllowedFileTypes());
				return false;
			}

			if (!isValidFileName(file.getOriginalFilename())) {
				bindingResult.reject("file", "Invalid file name: " + file.getOriginalFilename());
				return false;
			}
		}

		return true;
	}

	/**
	 *
	 * @param fileName
	 * @return
	 *    파일 이름은 영숫자와 점(.), 대시(-), 언더스코어(_)를 포함 가능.
	 * 	파일 이름에 상위 디렉토리 경로(..)가 포함되지 않도록 함.
	 */
	private boolean isValidFileName(String fileName) {
		return fileName != null && fileName.matches("^[a-zA-Z0-9._-]+$") && !fileName.contains("..");
	}
}
