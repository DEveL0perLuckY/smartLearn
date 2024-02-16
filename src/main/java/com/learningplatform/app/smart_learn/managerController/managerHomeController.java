package com.learningplatform.app.smart_learn.managerController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class managerHomeController {
    @GetMapping
    public String getManager() {
        return "managerHome/manager";
    }

}
