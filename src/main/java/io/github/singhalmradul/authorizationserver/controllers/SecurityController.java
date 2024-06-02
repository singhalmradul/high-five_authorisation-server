package io.github.singhalmradul.authorizationserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import io.github.singhalmradul.authorizationserver.exceptions.SignUpException;
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
    public String signUpSubmit(@ModelAttribute SignUpUser user, Model model) {

        model.addAttribute("user", user);

        try {
            jpaUserDetailsService.saveUserWithDetials(user);
        } catch (UsernameNotFoundException | SignUpException e) { // some targeted exceptions are wrapped in UsernameNotFoundException
            model.addAttribute("error", e.getMessage());

            return "sign-up";
        }

        return "sign-up-success";
    }

}
