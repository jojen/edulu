package org.jojen.wikistudy.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/course")
public class CourseController {
    protected static final int DEFAULT_PAGE_NUM = 0;
    protected static final int DEFAULT_PAGE_SIZE = 5;

    @Inject
    protected CourseService personService;

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(CourseController.class);

    @RequestMapping(value = "/list")
    public String list(
            @RequestParam(value = "page", required = false) Integer page,
            Model model) {
        int pageNum = page != null ? page : DEFAULT_PAGE_NUM;
        Page<Course> paging = personService.findAll(pageNum, DEFAULT_PAGE_SIZE);
        model.addAttribute("page", paging);
        return "/course/list";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public @ModelAttribute
	Course create(Model model) {
        Course course = new Course();
        return course;
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String createOnSubmit(@Valid Course course,
            BindingResult bindingResult, Model model) {
        LOGGER.debug("create course={}", course);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("validation error={}", bindingResult.getModel());
            model.addAllAttributes(bindingResult.getModel());
            return "/course/form";
        }
        personService.insert(course);
        return "redirect:/course/list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model) {
        Course course = personService.findById(id);
        model.addAttribute(course);
        return "/course/form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editOnSubmit(@Valid Course course,
            BindingResult bindingResult, Model model) {
        LOGGER.debug("edit course={}", course);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("validation error={}", bindingResult.getModel());
            model.addAllAttributes(bindingResult.getModel());
            return "/course/form";
        }
        personService.update(course);
        return "redirect:/course/list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(
            @RequestParam(value = "page", required = false) Integer page,
            @PathVariable("id") Integer id) {
        LOGGER.debug("delete id={}", id);
        personService.deleteById(id);

        return "redirect:/course/list";
    }

}
