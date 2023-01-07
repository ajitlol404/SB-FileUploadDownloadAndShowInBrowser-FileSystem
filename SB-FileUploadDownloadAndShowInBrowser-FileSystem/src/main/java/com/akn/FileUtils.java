package com.akn;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

	public static String saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {

		Path uploadPath = Paths.get(uploadDir);

		if (!(Files.exists(uploadPath))) {
			Files.createDirectories(uploadPath);
		}

		String fileCode = RandomStringUtils.randomAlphanumeric(8);

		String finalImageName = fileCode + "-" + fileName;

		try (InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadPath.resolve(finalImageName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); // Override existing file with same
																					// name
		} catch (IOException e) {
			throw new IOException("Could not save file : " + fileName, e);
		}

		return finalImageName;

	}

}
