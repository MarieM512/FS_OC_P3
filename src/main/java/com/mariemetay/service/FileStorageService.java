package com.mariemetay.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private String fileStorageLocation = "src/main/resources/img/";
    private String baseUrl = "http://localhost:3001/backend/img/";

    public String storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = Paths.get(fileStorageLocation).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return baseUrl + fileName;
        } catch (IOException e) {
            throw new FileStorageException("Couldn't store file " + fileName + ".Please try again !", e);
        }
    }
}

class FileStorageException extends RuntimeException {
    
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
