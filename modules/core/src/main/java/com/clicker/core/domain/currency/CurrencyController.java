package com.clicker.core.domain.currency;


import com.clicker.core.domain.currency.models.FullCurrencyResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/currencies")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService service;

    @GetMapping("/{id}/wallets")
    public ResponseEntity<?> getWallets(@PathVariable("id") @Positive(message = "Currency id should be positive!") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCurrencyResponseDto> get(@PathVariable("id")
                                                       @Positive(message = "Currency id should be positive!") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findWithWalletsById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestParam("password")
                                    @NotBlank(message = "Password shouldn't be empty!") String password,
                                    @PathVariable("id")
                                    @Positive(message = "Currency id should be positive!") Long id) {
        service.deleteByAdminPasswordAndId(password, id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
