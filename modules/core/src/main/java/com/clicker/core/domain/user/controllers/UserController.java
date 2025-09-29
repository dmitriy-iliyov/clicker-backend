package com.clicker.core.domain.user.controllers;


import com.clicker.core.domain.user.UserFacade;
import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.messaging.confirmation.ConfirmationService;
import com.clicker.core.security.core.models.token.models.TokenUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@Tag(
        name = "Users",
        description = "Public users method"
)
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade facade;
    private final ConfirmationService confirmationService;

    @Operation(description = "Create user")
    @PostMapping
    public ResponseEntity<String> create(@Parameter(description = "user")
                                        @RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        facade.save(userRegistrationDto);
        confirmationService.sendConfirmationMessage(userRegistrationDto.getEmail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "A confirmation email has been sent to your email address." +
                        "Please check your inbox to complete registration."
                );
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_UNCONFIRMED_USER')")
    public ResponseEntity<?> get(@AuthenticationPrincipal TokenUserDetails tokenUserDetails){
        UserResponseDto userResponseDto = facade.findById(tokenUserDetails.getUserId());
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/me/no-body")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> update(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                    @RequestBody UserUpdateDto userUpdateDto) {
        facade.update(tokenUserDetails.getUserId(), userUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> updateWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                              @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.update(tokenUserDetails.getUserId(), userUpdateDto));
    }

    @Operation(description = "Get all users")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(value = "filter", required = false) UserFilterDto filter,
                                    @RequestParam(value = "page", defaultValue = "0")
                                    @PositiveOrZero(message = "Page should be positive!") int page,
                                    @RequestParam(value = "size", defaultValue = "10")
                                    @Positive(message = "Size should be positive!") int size) {
        PagedDataDto<PublicUserResponseDto> users = facade.findAll(filter, PageRequest.of(page, size));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @Deprecated(forRemoval = true)
    @GetMapping("/random")
    public ResponseEntity<?> getAllRandom() {
        Random random = new Random();
        PagedDataDto<PublicUserResponseDto> users = facade.findAll(new UserFilterDto("username"), PageRequest.of(random.nextInt(11), random.nextInt(1, 11)));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @DeleteMapping("/me/{password}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> delete(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                    @PathVariable("password")
                                    @NotBlank(message = "Password shouldn't be empty!") String password) {
        facade.deleteByPassword(tokenUserDetails.getUserId(), password);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}