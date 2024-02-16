package com.learningplatform.app.smart_learn.adminController;

import com.learningplatform.app.smart_learn.domain.Role;
import com.learningplatform.app.smart_learn.model.UserDTO;
import com.learningplatform.app.smart_learn.repos.RoleRepository;
import com.learningplatform.app.smart_learn.service.RoleService;
import com.learningplatform.app.smart_learn.service.UserService;
import com.learningplatform.app.smart_learn.util.CustomCollectors;
import com.learningplatform.app.smart_learn.util.ReferencedWarning;
import com.learningplatform.app.smart_learn.util.WebUtils;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(final UserService userService, final RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("roleIdValues", roleRepository.findAll(Sort.by("roleId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Role::getRoleId, Role::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") final UserDTO userDTO) {
        return "user/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("user") @Valid final UserDTO userDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/add";
        }
        userService.create(userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.create.success"));
        return "redirect:/admin/users";
    }

    @Autowired
    RoleService roleService;

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable(name = "userId") final Integer userId, final Model model) {
        model.addAttribute("user", userService.get(userId));
        model.addAttribute("roles", roleService.findAll());
        return "user/edit";
    }

    @PostMapping("/edit/{userId}")
    public String edit(@PathVariable(name = "userId") final Integer userId,
            @ModelAttribute("user") @Valid final UserDTO userDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.update(userId, userDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("user.update.success"));
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{userId}")
    public String delete(@PathVariable(name = "userId") final Integer userId,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = userService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            userService.delete(userId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("user.delete.success"));
        }
        return "redirect:/admin/users";
    }

}
