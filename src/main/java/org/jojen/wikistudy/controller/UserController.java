package org.jojen.wikistudy.controller;


import org.jojen.wikistudy.entity.User;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
public class UserController {

	@Inject
	UserService userService;

	@Inject
	CourseService courseRepository;

	private final static Logger log = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String profile(Model model) {
		return "redirect:/";
	}

    /*
	@RequestMapping(value = "/user/{login}")
    public String publicProfile(Model model, @PathVariable("login") String login) {
        User profiled = userService.;
        User user = userRepository.getUserFromSession();

        return publicProfile(model, profiled, user);
    }*/

	private String publicProfile(Model model, User profiled, User user) {
		if (profiled.equals(user)) return profile(model);

		model.addAttribute("profiled", profiled);
		model.addAttribute("user", user);
		return "/user/public";
	}

}