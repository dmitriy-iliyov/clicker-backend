package com.clicker;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clicker")
@RequiredArgsConstructor
public class ClickerUiController {

    @Deprecated(forRemoval = true)
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String clickerForm() {
        return "clicker";
    }
}
