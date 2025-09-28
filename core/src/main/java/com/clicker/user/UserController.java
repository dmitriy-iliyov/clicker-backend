package com.clicker.user;


import com.clicker.messaging.confirmation.ConfirmationService;
import com.example.clicker.security.core.models.token.models.TokenUserDetails;
import com.example.clicker.user.models.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@Tag(
        name = "Users",
        description = "Public users method"
)
@Log4j2
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final ConfirmationService confirmationService;

    // delete in future
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "user_registration_form";
    }

    @Operation(description = "Create user")
    @PostMapping
    public ResponseEntity<String> createUser(@Parameter(description = "user")
                                        @RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        userFacade.save(userRegistrationDto);
        confirmationService.sendConfirmationMessage(userRegistrationDto.getEmail());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "A confirmation email has been sent to your email address." +
                        "Please check your inbox to complete registration."
                );
    }

    // delete in future
    @GetMapping("/login")
    public String loginUserForm(){
        return "user_logging_form";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_UNCONFIRMED_USER')")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal TokenUserDetails tokenUserDetails){
        UserResponseDto userResponseDto = userFacade.findById(tokenUserDetails.getUserId());
        return ResponseEntity.ok(userResponseDto);
    }

    // delete in future
    @GetMapping("/user/profile")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_UNCONFIRMED_USER')")
    public String profile(@AuthenticationPrincipal TokenUserDetails userDetails, Model model) {
        UserResponseDto userResponseDTO = userFacade.findById(userDetails.getUserId());
        model.addAttribute("user", userResponseDTO);
        return "user_profile";
    }

    // delete in future
    @GetMapping("/user/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String updateUserForm() {
        return "user_edit_form";
    }

    @PutMapping("/user/no-body")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> updatedUser(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                         @RequestBody UserUpdateDto userUpdateDto) {
        userFacade.update(tokenUserDetails.getUserId(), userUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> updatedUserWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                                   @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userFacade.update(tokenUserDetails.getUserId(), userUpdateDto));
    }

    @Operation(description = "Get all users")
    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(value = "filter", required = false) UserFilterDto filter,
                                         @RequestParam(value = "page", defaultValue = "0")
                                         @PositiveOrZero(message = "Page should be positive!") int page,
                                         @RequestParam(value = "size", defaultValue = "10")
                                         @Positive(message = "Size should be positive!") int size) {
        PagedDataDto<PublicUserResponseDto> users = userFacade.findAll(filter, PageRequest.of(page, size));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    // delete in future
    @GetMapping("/random")
    public ResponseEntity<?> getAllUsersRandom() {
        Random random = new Random();
        PagedDataDto<PublicUserResponseDto> users = userFacade.findAll(new UserFilterDto("username"), PageRequest.of(random.nextInt(11), random.nextInt(1, 11)));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users);
    }

    @DeleteMapping("/user/{password}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                        @PathVariable("password")
                                        @NotBlank(message = "Password shouldn't be empty!") String password) {
        userFacade.deleteByPassword(tokenUserDetails.getUserId(), password);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}