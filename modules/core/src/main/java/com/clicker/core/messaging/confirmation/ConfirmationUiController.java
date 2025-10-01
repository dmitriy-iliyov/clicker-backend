package com.clicker.core.messaging.confirmation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/confirmation")
public class ConfirmationUiController {

    @GetMapping("/email/**")
    private String getConfirmingPage(){
        return "email_confirmation";
    }
}
