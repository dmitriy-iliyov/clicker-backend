package com.clicker.core.domain.user.controllers;

import com.clicker.core.domain.user.UserFacade;
import com.clicker.core.domain.user.models.dto.UserResponseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserFacade facade;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> get(@PathVariable("id") @UUID(message = "Invalid id format!") String id) {
        UserResponseDto userResponseDto = facade.findById(java.util.UUID.fromString(id));
        return ResponseEntity.ok(userResponseDto);
    }

    //mb add password check
    @DeleteMapping("/{id}/{password}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") @UUID(message = "Invalid id format!") String id,
                                    @PathVariable(value = "password", required = false)
                                    @NotBlank(message = "Password shouldn't be empty!") String password) {
        facade.deleteById(java.util.UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
