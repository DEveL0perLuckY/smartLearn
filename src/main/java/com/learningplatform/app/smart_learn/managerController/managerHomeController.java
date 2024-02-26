package com.learningplatform.app.smart_learn.managerController;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learningplatform.app.smart_learn.domain.Course;
import com.learningplatform.app.smart_learn.domain.LearningContent;
import com.learningplatform.app.smart_learn.domain.User;
import com.learningplatform.app.smart_learn.model.CourseDTO;
import com.learningplatform.app.smart_learn.model.LearningContentDTO;
import com.learningplatform.app.smart_learn.repos.CourseRepository;
import com.learningplatform.app.smart_learn.repos.LearningContentRepository;
import com.learningplatform.app.smart_learn.repos.UserRepository;
import com.learningplatform.app.smart_learn.service.CourseService;
import com.learningplatform.app.smart_learn.service.LearningContentService;
import com.learningplatform.app.smart_learn.util.WebUtils;

import jakarta.validation.Valid;

@SuppressWarnings("null")
@Controller
@RequestMapping("/manager")
public class managerHomeController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getManager() {
        return "managerHome/manager";
    }

    @GetMapping("/profile")
    public String getUserprofile(Model m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).get();
        m.addAttribute("obj", user);
        return "managerHome/managerProfile";
    }

    @GetMapping("/courses")
    public String list(final Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "managerHome/list";
    }

    @GetMapping("/courses/add")
    public String add(@ModelAttribute("course") final CourseDTO courseDTO) {
        return "managerHome/add";
    }

    @PostMapping("/courses/add")
    public String add(@ModelAttribute("course") @Valid final CourseDTO courseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "managerHome/add";
        }
        courseService.create(courseDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("course.create.success"));
        return "redirect:/manager/courses";
    }

    @GetMapping("/courses/edit/{courseId}")
    public String edit(@PathVariable(name = "courseId") final Integer courseId, final Model model) {
        model.addAttribute("course", courseService.get(courseId));
        return "managerHome/edit";
    }

    @PostMapping("/courses/edit/{courseId}")
    public String edit(@PathVariable(name = "courseId") final Integer courseId,
            @ModelAttribute("course") @Valid final CourseDTO courseDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "managerHome/edit";
        }
        courseService.update(courseId, courseDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("course.update.success"));
        return "redirect:/manager/courses";
    }
    // todo delete the course
    // @PostMapping("/courses/delete/{courseId}")
    // public String delete(@PathVariable(name = "courseId") final Integer courseId,
    // final RedirectAttributes redirectAttributes) {
    // final ReferencedWarning referencedWarning =
    // courseService.getReferencedWarning(courseId);
    // if (referencedWarning != null) {
    // redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
    // WebUtils.getMessage(referencedWarning.getKey(),
    // referencedWarning.getParams().toArray()));
    // } else {
    // courseService.delete(courseId);
    // redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO,
    // WebUtils.getMessage("course.delete.success"));
    // }
    // return "redirect:/manager/courses";
    // }

    @Autowired
    LearningContentRepository learningContentRepository;

    @Autowired
    LearningContentService learningContentService;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/learningContent/{courseId}")
    public String getmanageCourseDataget(@PathVariable(name = "courseId") final Integer courseId, final Model model,
            final RedirectAttributes redirectAttributes, @RequestParam(defaultValue = "0") int page) {

        int PAGE_SIZE = 3;

        Course course = courseRepository.findById(courseId).orElse(null);

        if (course == null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage("Error, something is wrong."));
            return "redirect:/manager"; // You can replace "errorPage" with the actual error page name
        }

        model.addAttribute("courseObj", course);

        Page<LearningContent> x = learningContentRepository.findByCourse(course, PageRequest.of(page, PAGE_SIZE));

        model.addAttribute("learningContentList", x);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", x.getTotalPages());

        int nextPage = page + 1;
        int previousPage = page - 1;
        int firstPage = 0;
        int lastPage = x.getTotalPages() - 1;

        model.addAttribute("firstPageUrl", "/manager/learningContent/" + courseId + "?page=" + firstPage);
        model.addAttribute("previousPageUrl", "/manager/learningContent/" + courseId + "?page=" + previousPage);
        model.addAttribute("nextPageUrl", "/manager/learningContent/" + courseId + "?page=" + nextPage);
        model.addAttribute("lastPageUrl", "/manager/learningContent/" + courseId + "?page=" + lastPage);

        int startPage = Math.max(0, page - 2);
        int endPage = Math.min(lastPage, page + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "managerHome/learningContent";
    }

    @GetMapping("/learningContentAdd/{courseId}")
    public String getmanageCourseAdd(@PathVariable(name = "courseId") final Integer courseId, final Model model) {
        model.addAttribute("courseObj", courseRepository.findById(courseId).get());
        LearningContentDTO contentDTO = new LearningContentDTO();
        contentDTO.setCourse(courseId);
        model.addAttribute("obj", contentDTO);
        return "managerHome/learningContentAdd";
    }

    @PostMapping("/learningContent")
    public String learningContentpost(@ModelAttribute("obj") LearningContentDTO learningContentDTO,
            final RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            LearningContent learningContent = new LearningContent();

            learningContent = learningContentService.mapToEntity(learningContentDTO, learningContent);
            learningContent.setUnit(learningContentDTO.getUnit());
            if (!file.isEmpty()) {
                learningContent.setPostImage(file.getBytes());
            }
            learningContentRepository.save(learningContent);

        } catch (Exception e) {
        }
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("Learning Content Added successfully"));
        return "redirect:/manager/learningContent/" + learningContentDTO.getCourse();
    }

    @GetMapping("/learningContent/delete/{learningContentId}")
    public String LearningContentDelete(@PathVariable(name = "learningContentId") final Integer learningContentId,
            final RedirectAttributes redirectAttributes, final Model model) {

        int a = learningContentRepository.findById(learningContentId).get().getCourse().getCourseId();
        learningContentRepository.deleteById(learningContentId);

        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("Learning Content Delete successfully"));

        return "redirect:/manager/learningContent/" + a;
    }

    @GetMapping("/learningContent/image/{id}")
    public ResponseEntity<byte[]> getPost(@PathVariable("id") int id, Model model) {
        LearningContent post = learningContentRepository.findById(id).get();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(post.getPostImage());
    }

    @GetMapping("/learningContent/edit/{learningContentId}")
    public String LearningContentEdit(@PathVariable(name = "learningContentId") final Integer learningContentId,
            final Model model) {

        model.addAttribute("learningContent", learningContentService
                .mapToDTO(learningContentRepository.findById(learningContentId).get(), new LearningContentDTO()));
        return "managerHome/learningContentEdit";
    }

    @PostMapping("/learningContent/edit/{learningContentId}")
    public String LearningContentEditPost(@PathVariable(name = "learningContentId") final Integer learningContentId,
            @ModelAttribute("learningContent") @Valid final LearningContentDTO learningContentDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "managerHome/learningContentEdit";
        }
        LearningContent learningContent = new LearningContent();
        learningContent = learningContentService.mapToEntity(learningContentDTO, new LearningContent());
        learningContent.setContentId(learningContentId);

        if (file != null && !file.isEmpty()) {
            learningContent.setPostImage(file.getBytes());
        } else {
            learningContent.setPostImage(learningContentRepository.findById(learningContentId).get().getPostImage());
        }
        learningContentRepository.save(learningContent);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,
                WebUtils.getMessage("Learning Content update successfully"));
        return "redirect:/manager/learningContent/" + learningContentDTO.getCourse();
    }

}
