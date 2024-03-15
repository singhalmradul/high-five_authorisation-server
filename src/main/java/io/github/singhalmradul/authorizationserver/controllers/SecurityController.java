package io.github.singhalmradul.authorizationserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import io.github.singhalmradul.authorizationserver.model.SignUpUser;
import io.github.singhalmradul.authorizationserver.services.JpaUserDetailsService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SecurityController {

    JpaUserDetailsService jpaUserDetailsService;

    @GetMapping("/log-in")
    public String loginForm() {

        return "log-in";
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {

        model.addAttribute("user", new SignUpUser());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmitString(
        @ModelAttribute SignUpUser user,
        Model model
    ) {
        try {
            jpaUserDetailsService.saveUserWithDetials(user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "sign-up";
        }

        return "redirect:/log-in";
    }

}
