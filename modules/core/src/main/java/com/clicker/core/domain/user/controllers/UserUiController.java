package com.clicker.core.domain.user.controllers;

import com.clicker.auth.TokenUserDetails;
import com.clicker.core.domain.user.UserFacade;
import com.clicker.core.domain.user.models.dto.UserRegistrationRequest;
import com.clicker.core.domain.user.models.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/users")
@RequiredArgsConstructor
public class UserUiController {

    private final UserFacade facade;

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("user", new UserRegistrationRequest());
        return "user_registration_form";
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "user_logging_form";
    }

    @GetMapping("/me/profile")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_UNCONFIRMED_USER')")
    public String getProfile(@AuthenticationPrincipal TokenUserDetails userDetails, Model model) {
        UserResponseDto userResponseDTO = facade.findById(userDetails.getUserId());
        model.addAttribute("user", userResponseDTO);
        return "user_profile";
    }

    @GetMapping("/me/update")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String getUpdateForm() {
        return "user_edit_form";
    }
}
