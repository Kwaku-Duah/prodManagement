package com.ecommerce.demo_app.Controllers;

import com.ecommerce.demo_app.Models.Auth;
import com.ecommerce.demo_app.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("auth", new Auth());
        return "authuser/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute Auth auth, Model model) {
        if (authService.findByEmail(auth.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "authuser/signup";
        }
        authService.saveUser(auth);
        return "redirect:/authuser/signin";
    }

    @GetMapping("/signin")
    public String showSigninForm(Model model) {
        model.addAttribute("auth", new Auth());
        return "authuser/signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute Auth auth, Model model) {
        Auth existingUser = authService.findByEmail(auth.getEmail());
        if (existingUser != null && authService.checkPassword(existingUser, auth.getPassword())) {
            return "redirect:/products";
        }
        model.addAttribute("error", "Invalid email or password");
        return "authuser/signin";
    }
}
