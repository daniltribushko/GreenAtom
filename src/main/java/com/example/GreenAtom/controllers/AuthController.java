package com.example.GreenAtom.controllers;

import com.example.GreenAtom.models.dto.request.AuthRequest;
import com.example.GreenAtom.models.dto.response.ExceptionResponse;
import com.example.GreenAtom.models.dto.response.JwtTokenResponse;
import com.example.GreenAtom.models.dto.response.UserResponse;
import com.example.GreenAtom.services.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author Tribushko Danil
 * Контроллер для авторизации и аутенфикации
 */
@Tag(name = "Auth Controller")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthUserService authUserService;

    @Autowired
    public AuthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Operation(summary = "Sign-in user", description = "Return user jwt token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is logged in",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtTokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Wrong username or password",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenResponse> signIn(@Valid
                                                   @RequestBody
                                                   AuthRequest request) {
        JwtTokenResponse response = authUserService.signIn(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @Operation(summary = "Sign-up user", description = "Return user response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "409", description = "User with username already exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUp(@Valid
                                               @RequestBody
                                               AuthRequest request) {
        UserResponse response = authUserService.signUp(request);
        return ResponseEntity.created(URI.create("api/user/" + response.getId()))
                .body(response);
    }
}
