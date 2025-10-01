package com.clicker.core.domain.wallets;


import com.clicker.auth.TokenUserDetails;
import com.clicker.core.domain.wallets.models.dto.WalletCreateDto;
import com.clicker.core.domain.wallets.models.dto.WalletUpdateDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/wallets")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class WalletController {

    private final WalletFacade walletFacade;

    @PostMapping
    public ResponseEntity<?> createWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                              @RequestBody @Valid WalletCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(walletFacade.save(tokenUserDetails.getUserId(), dto));
    }

    @PostMapping("/no-body")
    public ResponseEntity<?> create(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                    @RequestBody @Valid WalletCreateDto dto) {
        walletFacade.save(tokenUserDetails.getUserId(), dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walletFacade.findAllWithCurrencyByUserId(tokenUserDetails.getUserId()));
    }

    @PostMapping("/{id}/main")
    public ResponseEntity<?> setMain(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                     @PathVariable("id") @Positive(message = "Wallet id should be positive!") Long id,
                                     HttpServletResponse response) {
        walletFacade.setMainWallet(tokenUserDetails.getUserId(), id, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                              @PathVariable("id") @NotNull(message = "Wallet id shouldn't be blank!")
                                              @Positive(message = "Wallet id shouldn't be negative!") Long id,
                                              @RequestBody WalletUpdateDto dto) {
        dto.setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walletFacade.update(tokenUserDetails.getUserId(), dto));
    }

    @PutMapping("/{id}/no-body")
    public ResponseEntity<?> update(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                    @PathVariable("id") @NotNull(message = "Wallet id shouldn't be blank!")
                                    @Positive(message = "Wallet id shouldn't be negative!") Long id,
                                    @RequestBody WalletUpdateDto dto) {
        dto.setId(id);
        walletFacade.update(tokenUserDetails.getUserId(), dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                    @PathVariable("id") @Positive(message = "Wallet id should be positive!") Long id) {
        walletFacade.deleteByUserIdNId(tokenUserDetails.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
