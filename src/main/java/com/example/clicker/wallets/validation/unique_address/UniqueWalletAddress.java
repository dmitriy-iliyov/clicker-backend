package com.example.clicker.wallets.validation.unique_address;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueCreateWalletAddressValidator.class, UniqueUpdateWalletAddressValidator.class})
@Documented
public @interface UniqueWalletAddress {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
