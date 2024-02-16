package com.learningplatform.app.smart_learn.loginController;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learningplatform.app.smart_learn.domain.Role;
import com.learningplatform.app.smart_learn.domain.User;
import com.learningplatform.app.smart_learn.model.UserDTO;
import com.learningplatform.app.smart_learn.repos.RoleRepository;
import com.learningplatform.app.smart_learn.repos.UserRepository;
import com.learningplatform.app.smart_learn.util.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("null")
@Controller
public class RegistrationController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository rolesRepository;

    @GetMapping("/unAuthorized")
    public String unAuthorized() {
        return "Pages/unAuthorized";
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("obj", new UserDTO());
        return "Pages/SingUp";
    }

    @PostMapping("/signup")
    public String registration(@Valid @ModelAttribute("obj") UserDTO userDTO,
            final RedirectAttributes redirectAttributes, BindingResult result, Model model) {
        if (userAlreadyRegistered(userDTO.getEmail(), result)) {
            return "Pages/SingUp";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "Pages/SingUp";
        }

        Role roleUser = rolesRepository.findById(Constant.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("User role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);
        User user = new User();
        try {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setRoleId(roles);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                    WebUtils.getMessage("Registration successfully!"));
            return "redirect:/login";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage("Registration failed. Please try again."));
            return "redirect:/login?fail";
        }

    }

    private boolean userAlreadyRegistered(String email, BindingResult result) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            result.rejectValue("email", null, "User already registered!");
            return true;
        }
        return false;
    }
}
