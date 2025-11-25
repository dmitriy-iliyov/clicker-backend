package com.clicker.core.domain.wallets.models.dto;

import com.clicker.core.domain.wallets.validation.address.annotation.WalletAddress;
import com.clicker.core.domain.wallets.validation.unique_address.UniqueWalletAddress;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@WalletAddress
@UniqueWalletAddress
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class WalletUpdateDto extends BaseWalletDto {

        @NotNull(message = "Wallet id shouldn't be blank!")
        @Positive(message = "Wallet id shouldn't be negative!")
        protected Long id;

        public WalletUpdateDto(Long id, Long currencyId, String address) {
                super(currencyId, address);
                this.id = id;
        }
}
