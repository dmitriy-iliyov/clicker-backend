package com.example.clicker.wallets;


import com.example.clicker.security.core.models.token.models.TokenUserDetails;
import com.example.clicker.wallets.models.dto.WalletCreateDto;
import com.example.clicker.wallets.models.dto.WalletUpdateDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users/user/wallets")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class UserWalletController {

    private final WalletFacade walletFacade;

    @PostMapping("/no-body")
    public ResponseEntity<?> addWallet(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                       @RequestBody @Valid WalletCreateDto walletCreateDto) {
        walletFacade.save(tokenUserDetails.getUserId(), walletCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/no-body")
    public ResponseEntity<?> addWalletWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                                 @RequestBody @Valid WalletCreateDto walletCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(walletFacade.save(tokenUserDetails.getUserId(), walletCreateDto));
    }

    @GetMapping
    public ResponseEntity<?> getAllWallets(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walletFacade.findAllWithCurrencyByUserId(tokenUserDetails.getUserId()));
    }

    @PostMapping("/{id}/main")
    public ResponseEntity<?> setMainWalletById(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                               @PathVariable
                                               @Positive(message = "Wallet id should be positive!") Long id,
                                               HttpServletResponse response) {
        walletFacade.setMainWallet(tokenUserDetails.getUserId(), id, response);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/no-body")
    public ResponseEntity<?> updateWalletById(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                              @RequestBody @Valid WalletUpdateDto walletUpdateDto) {
        walletFacade.update(tokenUserDetails.getUserId(), walletUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateWalletByIdWithReturn(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                                        @RequestBody @Valid WalletUpdateDto walletUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(walletFacade.update(tokenUserDetails.getUserId(), walletUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWalletById(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                              @PathVariable("id")
                                              @Positive(message = "Wallet id should be positive!") Long id) {
        walletFacade.deleteByUserIdNId(tokenUserDetails.getUserId(), id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
