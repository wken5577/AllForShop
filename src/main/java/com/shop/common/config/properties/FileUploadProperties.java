package com.shop.common.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@ConfigurationProperties(prefix = "file.upload")
@Getter
public class FileUploadProperties {

	private final String uploadDir;
	private final long maxFileSize;
	private final int maxFileCount;
	private final List<String> allowedFileTypes;

	@ConstructorBinding
	public FileUploadProperties(String uploadDir, long maxFileSize, int maxFileCount, List<String> allowedFileTypes) {
		this.uploadDir = uploadDir;
		this.maxFileSize = maxFileSize;
		this.maxFileCount = maxFileCount;
		this.allowedFileTypes = allowedFileTypes;
	}
}
