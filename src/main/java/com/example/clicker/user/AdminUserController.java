package com.example.clicker.user;

import com.example.clicker.user.models.dto.UserResponseDto;
import com.example.clicker.user.models.dto.UserUpdateDto;
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

    private final UserFacade userFacade;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id")
                                         @UUID(message = "Invalid id format!") String id) {
        UserResponseDto userResponseDto = userFacade.findById(java.util.UUID.fromString(id));
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}/no-body")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updatedUserById(@PathVariable("id")
                                             @UUID(message = "Invalid id format!") String id,
                                             @RequestBody UserUpdateDto userUpdateDto) {
        userFacade.update(java.util.UUID.fromString(id), userUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updatedUserByIdWithReturn(@PathVariable("id")
                                                       @UUID(message = "Invalid id format!") String id,
                                                       @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userFacade.update(java.util.UUID.fromString(id), userUpdateDto));
    }

    //mb add password check
    @DeleteMapping("/{id}/{password}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUserById(@PathVariable("id")
                                            @UUID(message = "Invalid id format!") String id,
                                            @PathVariable(value = "password", required = false)
                                            @NotBlank(message = "Password shouldn't be empty!") String password) {
        userFacade.deleteById(java.util.UUID.fromString(id));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
