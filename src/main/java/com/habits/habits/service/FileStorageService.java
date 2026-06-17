package com.habits.habits.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot store empty file.");
            }
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String storedFilename = UUID.randomUUID() + fileExtension; // Create a new file name
            Path destinationFile = Paths.get(uploadDir).resolve(storedFilename).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile);
            return storedFilename; // Or return destinationFile.toString() to get the absolute path
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    // Add other methods if needed, like deleteFile, etc.
}
