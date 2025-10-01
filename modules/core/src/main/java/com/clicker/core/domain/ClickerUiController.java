package com.clicker.core.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/clicker")
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequiredArgsConstructor
public class ClickerUiController {

    @GetMapping
    public String clickerForm() {
        return "clicker";
    }
}
