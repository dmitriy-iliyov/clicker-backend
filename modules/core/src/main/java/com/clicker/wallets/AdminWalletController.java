package com.clicker.wallets;

import com.clicker.wallets.models.dto.WalletCreateDto;
import com.clicker.wallets.models.dto.WalletUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminWalletController {

    private final WalletFacade walletFacade;

    @PostMapping("/{userId}")
    public ResponseEntity<?> addWalletByUser(@PathVariable ("userId")
                                             @UUID(message = "Invalid id format!") String userId,
                                             @RequestBody @Valid WalletCreateDto walletCreateDto) {
        walletFacade.save(java.util.UUID.fromString(userId), walletCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateWalletByUserIdNId(@PathVariable ("userId")
                                                     @UUID(message = "Invalid id format!") String userId,
                                                     @RequestBody @Valid WalletUpdateDto walletUpdateDto) {
        walletFacade.update(java.util.UUID.fromString(userId), walletUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWalletById(@PathVariable("id")
                                           @Positive(message = "Id should be positive!") Long id) {
        return ResponseEntity.ok(walletFacade.findFullById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWalletById(@PathVariable ("id")
                                              @Positive(message = "Wallet id should be positive!") Long id) {
        walletFacade.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
