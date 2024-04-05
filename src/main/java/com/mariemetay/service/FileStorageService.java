package com.mariemetay.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    @Value("${SERVER_PORT}")
    private String serverPort;
    private String fileStorageLocation = "src/main/resources/img/";

    public String storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String baseUrl = "http://localhost:" + serverPort + "/backend/img/";

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence " + fileName);
            }
            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
                Path targetLocation = Paths.get(fileStorageLocation).resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    
                return baseUrl + fileName;
            } else {
                throw new IOException("Please upload an image with png or jpeg extension.");
            }
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
