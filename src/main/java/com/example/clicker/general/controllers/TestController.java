package com.example.clicker.general.controllers;

import com.example.clicker.wallets.WalletsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final WalletsService walletsService;

    @GetMapping("/{isError}")
    public ResponseEntity<?> error(@PathVariable("isError") boolean isError) {
        if (isError) {
            throw new DataIntegrityViolationException("User with email not found.");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/test-FullWalletDtoById/{id}")
    public ResponseEntity<?> getFullWalletDto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(walletsService.findFullById(id));
    }

    @GetMapping("/test-FullWalletDto/{address}")
    public ResponseEntity<?> getFullWalletDto(@PathVariable("address") String address) {
        return ResponseEntity.ok(walletsService.findAllFullByAddress(address));
    }
}
