package com.clicker.currency;


import com.clicker.currency.models.dto.CurrencyCreateDto;
import com.clicker.currency.models.dto.CurrencyUpdateDto;
import com.clicker.currency.models.dto.FullCurrencyResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/currencies")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<?> createCurrency(@RequestBody @Valid CurrencyCreateDto currencyCreateDto) {
        currencyService.save(currencyCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<?> getWalletsByCurrencyId(@PathVariable("id")
                                                    @Positive(message = "Currency id should be positive!") Long id) {
        return ResponseEntity.ok(currencyService.findById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCurrencyResponseDto> getCurrencyById(@PathVariable("id")
                                                                   @Positive(message = "Currency id should be positive!") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(currencyService.findWithWalletsById(id));
    }

    @PatchMapping("/no-body")
    public ResponseEntity<?> updateCurrencyByPassword(@RequestParam("password")
                                                      @NotBlank(message = "Password shouldn't be empty!") String password,
                                                      @RequestBody @Valid CurrencyUpdateDto currencyUpdateDto) {
        currencyService.updateByAdminPassword(password, currencyUpdateDto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping
    public ResponseEntity<?> updateCurrencyByPasswordWithReturns(@RequestParam("password")
                                                      @NotBlank(message = "Password shouldn't be empty!") String password,
                                                      @RequestBody @Valid CurrencyUpdateDto currencyUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(currencyService.updateByAdminPassword(password, currencyUpdateDto));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAllCurrencies() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(currencyService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrencyByPasswordNId(@RequestParam("password")
                                                         @NotBlank(message = "Password shouldn't be empty!") String password,
                                                         @PathVariable("id")
                                                         @Positive(message = "Currency id should be positive!") Long id) {
        currencyService.deleteByAdminPasswordAndId(password, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
