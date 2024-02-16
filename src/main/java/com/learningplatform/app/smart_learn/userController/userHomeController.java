package com.learningplatform.app.smart_learn.userController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import java.io.IOException;
// import java.time.LocalDateTime;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

import com.learningplatform.app.smart_learn.domain.User;
import com.learningplatform.app.smart_learn.repos.UserRepository;

@Controller
@RequestMapping("/user")
public class userHomeController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String getUser() {
        return "userHome/user";
    }

    @GetMapping("/profile")
    public String getUserprofile(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).get();
        m.addAttribute("user", user);
        return "userHome/userProfile";
    }
}
