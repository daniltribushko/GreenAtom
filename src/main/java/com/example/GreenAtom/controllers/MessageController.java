package com.example.GreenAtom.controllers;

import com.example.GreenAtom.models.dto.request.MessageRequest;
import com.example.GreenAtom.models.dto.request.UpdateMessageRequest;
import com.example.GreenAtom.models.dto.response.ExceptionResponse;
import com.example.GreenAtom.models.dto.response.MessagesResponse;
import com.example.GreenAtom.models.dto.response.TopicResponse;
import com.example.GreenAtom.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;

/**
 * @author Tribushko Danil
 * <p>
 * Контроллер для работы с сообщениями
 */
@Tag(name = "Client Api")
@SecurityRequirement(name = "jwtAuth")
@RestController
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Secured("ROLE_USER")
    @PostMapping("/topic/{topicId}/message")
    @Operation(summary = "Create a new message", description = "Return topic with created message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic by id not found or User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<TopicResponse> create(Principal principal,
                                                @PathVariable
                                                @Min(value = 1, message = "Id can not be less than 1")
                                                Long topicId,
                                                @Valid
                                                @RequestBody
                                                MessageRequest message) {
        TopicResponse response = messageService.createMessage(principal.getName(), topicId, message);
        return ResponseEntity.created(URI.create("api/message/" + response.getMessages().get(0).getId()))
                .body(response);
    }

    @Secured("ROLE_USER")
    @PutMapping("/topic/{topicId}/message")
    @Operation(summary = "Update message in topic", description = "User must be message author or admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "409", description = "User not message author and not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic by id or message by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<TopicResponse> update(Principal principal,
                                                @PathVariable
                                                @Min(value = 1, message = "Id can not be less than 1")
                                                Long topicId,
                                                @Valid
                                                @RequestBody
                                                UpdateMessageRequest message) {
        TopicResponse response = messageService.updateTopic(principal.getName(), topicId, message);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/message/{id}")
    @Operation(summary = "Delete message in topic", description = "User must be message author or admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Message deleted",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "User not message author and not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic by id or message by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Object> delete(Principal principal,
                                         @PathVariable
                                         @Min(value = 1, message = "Id can not be less than 1")
                                         Long id) {
        messageService.delete(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_USER")
    @GetMapping("/topic/{topicId}/message/{id}")
    @Operation(summary = "Find message from topic", description = "Return topic with message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message find",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Topic by id or message by id not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<TopicResponse> findById(@PathVariable
                                                  @Min(value = 1, message = "Id can not be less than 1")
                                                  Long topicId,
                                                  @PathVariable
                                                  @Min(value = 1, message = "Id can not be less than 1")
                                                  Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findById(topicId, id));
    }

    @GetMapping("/all")
    @Secured("ROLE_USER")
    @Operation(summary = "Find all messages with pagination")
    public ResponseEntity<MessagesResponse> findAll(
            @Schema(description = "Number of page",
                    name = "page",
                    type = "int",
                    example = "0",
                    minimum = "0",
                    defaultValue = "0")
            @Min(value = 0, message = "Page can not be less than 0")
            @RequestParam(defaultValue = "0")
            int page,
            @Schema(description = "Count messages on one page",
                    name = "per_page",
                    type = "int",
                    example = "1",
                    minimum = "1",
                    defaultValue = "10")
            @Min(value = 1, message = "Messages count on one page can not be less than 1")
            @RequestParam(name = "per_page", defaultValue = "10")
            int perPage,
            @Schema(description = "Message author name",
                    name = "author id",
                    type = "string",
                    example = "author")
            @RequestParam(required = false)
            String author,
            @Schema(description = "Message topic name",
                    name = "topic name",
                    type = "string",
                    example = "Test topic")
            @RequestParam(required = false)
            String topic,
            @Schema(description = "Creation date of message",
                    name = "created date",
                    type = "localDateTime")
            @PastOrPresent(message = "Created date must be past or present")
            @RequestParam(required = false)
            LocalDateTime createdDate
    ) {
        return ResponseEntity.ok(messageService.findAll(page, perPage, author, topic, createdDate));
    }
}
