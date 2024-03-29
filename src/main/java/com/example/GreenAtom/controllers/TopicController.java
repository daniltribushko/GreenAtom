package com.example.GreenAtom.controllers;

import com.example.GreenAtom.models.dto.request.CreateTopicRequest;
import com.example.GreenAtom.models.dto.request.UpdateTopicRequest;
import com.example.GreenAtom.models.dto.response.ExceptionResponse;
import com.example.GreenAtom.models.dto.response.TopicResponse;
import com.example.GreenAtom.models.dto.response.TopicsResponse;
import com.example.GreenAtom.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

/**
 * @author Tribushko Danil
 * <p>
 * Контроллер для работы с топиками
 */
@SecurityRequirement(name = "jwtAuth")
@RestController
@RequestMapping("/topic")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    @Operation(tags = {"Client Api"}, summary = "Create a new topic with first message",
            description = "Return a topic with message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Topic created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Topic already exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<TopicResponse> create(Principal principal,
                                                @Valid
                                                @RequestBody
                                                CreateTopicRequest request) {
        TopicResponse response = topicService.create(principal.getName(), request);
        return ResponseEntity.created(URI.create("api/topic/" + response.getId()))
                .body(response);
    }

    @PutMapping()
    @Secured("ROLE_ADMIN")
    @Operation(tags = {"Admin Api"}, summary = "Update topic",
            description = "Secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic by id or user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<TopicResponse> update(Principal principal,
                                                @Valid
                                                @RequestBody
                                                UpdateTopicRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(topicService.update(principal.getName(), request));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(tags = {"Admin Api"}, summary = "Delete topic",
            description = "Secured by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Topic deleted"),
            @ApiResponse(responseCode = "404", description = "Topic by id or user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User not admin",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<Object> delete(Principal principal,
                                         @PathVariable
                                         @Min(value = 1, message = "Id can not be less than 1")
                                         Long id) {
        topicService.delete(principal.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    @Operation(tags = {"Client Api"}, summary = "Find topic by id", description = "Return a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic by id or user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    public ResponseEntity<TopicResponse> findById(@PathVariable
                                                  @Min(value = 1, message = "Id can not be less than 1")
                                                  Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.findById(id));
    }

    @GetMapping("/all")
    @Secured("ROLE_USER")
    @Operation(tags = {"Client Api"}, summary = "Find all topics with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topics found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TopicsResponse.class)))
    })
    public ResponseEntity<TopicsResponse> findAll(
            @Schema(description = "Number of page",
                    name = "page",
                    type = "int",
                    example = "0",
                    minimum = "0",
                    defaultValue = "0")
            @Min(value = 0, message = "Page can not be less than 0")
            @RequestParam(defaultValue = "0")
            int page,
            @Schema(description = "Count topic on one page",
                    name = "per_page",
                    type = "int",
                    example = "1",
                    minimum = "1",
                    defaultValue = "10")
            @Min(value = 1, message = "Topic count on one page can not be less than 1")
            @RequestParam(defaultValue = "10", name = "per_page")
            int perPage) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.findAll(page, perPage));
    }
}
