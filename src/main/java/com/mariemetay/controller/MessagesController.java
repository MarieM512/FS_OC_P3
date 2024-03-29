package com.mariemetay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mariemetay.model.dto.MessageDTO;
import com.mariemetay.model.response.Rental200;
import com.mariemetay.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Messages", description = "Related to messages in rentals")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    public MessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Contact the owner")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully send", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Rental200.class))
        }),
        @ApiResponse(responseCode = "400", description = "Some fields are empty", content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Rental200> postMessage(HttpServletRequest request, @RequestBody MessageDTO messageDTO) {
        String bearerToken = request.getHeader("Authorization");
        if (messageDTO.getMessage() == null || messageDTO.getUser_id() == null || messageDTO.getRental_id() == null) {
            return ResponseEntity.badRequest().build();
        } else if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            messageService.postMessage(messageDTO);
            Rental200 response = new Rental200();
            response.setMessage("Message send with success");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

}
