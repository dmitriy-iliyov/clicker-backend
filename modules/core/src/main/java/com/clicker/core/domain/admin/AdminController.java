package com.clicker.core.domain.admin;


import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final Validator validator;

    @GetMapping("/home")
    public String adminHome(){
        return "admin_home_page";
    }

//    @GetMapping("/admin/update")
//    public String updateAdminForm(@AuthenticationPrincipal TokenUserDetails tokenUserDetails, Model model){
//        UserResponseDto userResponseDTO = userService.findById(tokenUserDetails.getUserId());
//        if(userResponseDTO != null){
//            model.addAttribute("admin", userResponseDTO);
//            return "user_edit_form";
//        }
//        else{
//            log.error("Admin whit id {} not found in database.", tokenUserDetails.getUserId());
//            return "redirect:/user/login";
//        }
//    }

//    @PatchMapping("/admin/update")
//    public ResponseEntity<?> updatedAdmin(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
//                                         @ModelAttribute("admin") UserRegistrationDto userRegistrationDto){
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("X-info", "Update admin");
//
//        Long id = tokenUserDetails.getUserId();
//        SystemUserUpdateDto systemUserUpdateDto = userService.mapToUpdateDto(userRegistrationDto);
//        systemUserUpdateDto.setId(tokenUserDetails.getUserId());
//        if (!userService.existingById(id))
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .headers(httpHeaders)
//                    .build();
//        Set<ConstraintViolation<SystemUserUpdateDto>> bindingResult = validator.validate(systemUserUpdateDto);
//        if(!bindingResult.isEmpty())
//            return ResponseEntity
//                    .ok()
//                    .headers(httpHeaders)
//                    .body(MapUtils.bindingErrorsFromConstraintValidator(bindingResult));
//        userService.update(systemUserUpdateDto);
//        log.info("Updated admin : " + userRegistrationDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .headers(httpHeaders)
//                .build();
//    }
}