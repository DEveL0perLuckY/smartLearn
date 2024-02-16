package com.learningplatform.app.smart_learn.adminController;

import com.learningplatform.app.smart_learn.domain.User;
import com.learningplatform.app.smart_learn.model.UserProfileDTO;
import com.learningplatform.app.smart_learn.repos.UserRepository;
import com.learningplatform.app.smart_learn.service.UserProfileService;
import com.learningplatform.app.smart_learn.util.CustomCollectors;
import com.learningplatform.app.smart_learn.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/admin/userProfiles")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    public UserProfileController(final UserProfileService userProfileService,
            final UserRepository userRepository) {
        this.userProfileService = userProfileService;
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("userValues", userRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(User::getUserId, User::getUsername)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("userProfiles", userProfileService.findAll());
        return "userProfile/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("userProfile") final UserProfileDTO userProfileDTO) {
        return "userProfile/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("userProfile") @Valid final UserProfileDTO userProfileDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userProfile/add";
        }
        userProfileService.create(userProfileDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userProfile.create.success"));
        return "redirect:/admin/userProfiles";
    }

    @GetMapping("/edit/{userProfileId}")
    public String edit(@PathVariable(name = "userProfileId") final Integer userProfileId,
            final Model model) {
        model.addAttribute("userProfile", userProfileService.get(userProfileId));
        return "userProfile/edit";
    }

    @PostMapping("/edit/{userProfileId}")
    public String edit(@PathVariable(name = "userProfileId") final Integer userProfileId,
            @ModelAttribute("userProfile") @Valid final UserProfileDTO userProfileDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "userProfile/edit";
        }
        userProfileService.update(userProfileId, userProfileDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("userProfile.update.success"));
        return "redirect:/admin/userProfiles";
    }

    @PostMapping("/delete/{userProfileId}")
    public String delete(@PathVariable(name = "userProfileId") final Integer userProfileId,
            final RedirectAttributes redirectAttributes) {
        userProfileService.delete(userProfileId);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("userProfile.delete.success"));
        return "redirect:/admin/userProfiles";
    }

}
