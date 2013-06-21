package org.jojen.wikistudy.controller;


import org.jojen.wikistudy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final static Logger log = LoggerFactory.getLogger(AuthController.class);

    @Inject
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "login_error", required = false) boolean error,
						@RequestParam(value = "nextUrl", required = false) String url, Model model) {
        log.debug("Received request to show login page, error " + error);
        if (error) {
            model.addAttribute("error", "You have entered an invalid username or password!");
        }
		if(url!= null){
			model.addAttribute("nextUrl",url);
		}
        return "/auth/loginpage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
            @RequestParam(value = "j_username") String login,
            @RequestParam(value = "j_password") String password,
            Model model) {

        try {
            //userService.register(login, name, password);
            return "forward:/user/" + login;
        } catch (Exception e) {
            model.addAttribute("j_username", login);
            model.addAttribute("error", e.getMessage());
            return "/auth/registerpage";
        }
    }

    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String denied() {
        log.debug("Received request to show denied page");
        return "/auth/deniedpage";
    }

    @RequestMapping(value = "/registerpage", method = RequestMethod.GET)
    public String registerPage() {
        log.debug("Received request to show register page");
        return "/auth/registerpage";
    }
}