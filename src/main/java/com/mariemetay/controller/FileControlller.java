package com.mariemetay.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "File", description = "Permit to display image")
@RestController
public class FileControlller {

    private String fileStorageLocation = "src/main/resources/img";

    @Operation(summary = "Display image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful loading", content = @Content),
        @ApiResponse(responseCode = "404", description = "File does not exist", content = @Content),
        @ApiResponse(responseCode = "500", description = "Fail to load resource", content = @Content)
    })
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
