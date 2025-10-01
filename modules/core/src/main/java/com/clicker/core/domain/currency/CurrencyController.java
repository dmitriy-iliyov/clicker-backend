package com.clicker.core.domain.currency;


import com.clicker.core.domain.currency.models.dto.CurrencyCreateDto;
import com.clicker.core.domain.currency.models.dto.CurrencyUpdateDto;
import com.clicker.core.domain.currency.models.dto.FullCurrencyResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/currencies")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CurrencyController {

    private final CurrencyService service;
    private final CurrencyUpdateService updateService;

    public CurrencyController(CurrencyService service,
                              @Qualifier("currencyUpdateDecorator") CurrencyUpdateService updateService) {
        this.service = service;
        this.updateService = updateService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CurrencyCreateDto dto) {
        service.save(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{id}/wallets")
    public ResponseEntity<?> getWallets(@PathVariable("id") @Positive(message = "Currency id should be positive!") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCurrencyResponseDto> get(@PathVariable("id")
                                                       @Positive(message = "Currency id should be positive!") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findWithWalletsById(id));
    }

    @PatchMapping("/{id}/no-body")
    public ResponseEntity<?> update(@RequestParam("password")
                                    @NotBlank(message = "Password shouldn't be empty!") String password,
                                    @PathVariable("id") @Positive(message = "Currency id should be positive!") Long id,
                                    @RequestBody @Valid CurrencyUpdateDto dto) {
        dto.setId(id);
        updateService.updateByAdminPassword(password, dto);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateWithReturns(@PathVariable("id")
                                               @Positive(message = "Currency id should be positive!") Long id,
                                               @RequestParam("password")
                                               @NotBlank(message = "Password shouldn't be empty!") String password,
                                               @RequestBody @Valid CurrencyUpdateDto dto) {
        dto.setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateService.updateByAdminPassword(password, dto));
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
