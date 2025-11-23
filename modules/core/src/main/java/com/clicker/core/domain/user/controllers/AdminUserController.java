package com.clicker.core.domain.user.controllers;

import com.clicker.core.domain.user.UserFacade;
import com.clicker.core.domain.user.models.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserFacade facade;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") @UUID(message = "Invalid id format!") String id) {
        UserResponseDto userResponseDto = facade.findById(java.util.UUID.fromString(id));
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @UUID(message = "Invalid id format!") String id) {
        facade.deleteById(java.util.UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
