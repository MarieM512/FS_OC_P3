package com.mariemetay.controller;

import org.springframework.web.bind.annotation.RestController;



import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class FileControlller {

    private String fileStorageLocation = "src/main/resources/img";

    @GetMapping("/backend/img/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable("fileName") String fileName) throws FileNotFoundException {
        
        Path filePath = Paths.get(fileStorageLocation).resolve(fileName).normalize();
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.status(404).build();
            } else {
                return ResponseEntity.ok().body(resource);
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
