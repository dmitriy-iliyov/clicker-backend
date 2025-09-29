package com.clicker.core.domain.avatar;

import com.clicker.core.security.core.models.token.models.TokenUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {

    private final AvatarService avatarService;


    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAvatar(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                          @RequestParam("image") MultipartFile image) {
        avatarService.create(tokenUserDetails.getUserId(), image);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAvatar(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                          @RequestParam("image") MultipartFile image) {
        avatarService.create(tokenUserDetails.getUserId(), image);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/bytes", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getAvatarBytes(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.findBytesByUserId(tokenUserDetails.getUserId()));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/base64", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAvatarBase64(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(avatarService.findBase64ByUserId(tokenUserDetails.getUserId()));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping
    public ResponseEntity<?> deleteAvatar(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        avatarService.deleteByUserId(tokenUserDetails.getUserId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
