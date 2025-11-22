package com.clicker.core.domain.user.controllers;


import com.clicker.auth.TokenUserDetails;
import com.clicker.core.domain.user.UserFacade;
import com.clicker.core.domain.user.models.dto.*;
import com.clicker.core.messaging.confirmation.ConfirmationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(
        name = "Users",
        description = "Public users method"
)
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade facade;
    private final ConfirmationService confirmationService;

    @Operation(description = "Create user")
    @PostMapping
    public ResponseEntity<String> create(@Parameter(description = "user")
                                        @RequestBody @Valid UserRegistrationRequest request) {
        facade.save(request);
        confirmationService.sendConfirmationMessage(request.getEmail());
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
                                    @RequestParam("user") UserUpdateRequest request,
                                    @RequestParam("avatar") MultipartFile avatar) {
        facade.update(tokenUserDetails.getUserId(), request, avatar);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> updateWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                              @RequestParam("user") UserUpdateRequest request,
                                              @RequestParam("avatar") MultipartFile avatar) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(facade.update(tokenUserDetails.getUserId(), request, avatar));
    }

    @Operation(description = "Get all users")
    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute DefaultUserFilter filter) {
        PageDto<PublicUserResponseDto> users = facade.findAll(filter);
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