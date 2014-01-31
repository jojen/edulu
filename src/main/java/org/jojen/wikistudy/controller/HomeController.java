package org.jojen.wikistudy.controller;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	protected static final int DEFAULT_PAGE_NUM = 0;
	protected static final int DEFAULT_PAGE_SIZE = 10;

	@Inject
	protected CourseService courseService;

	private static final Logger log = LoggerFactory
											  .getLogger(HomeController.class);


	@RequestMapping(value = "/")
	public String home(@RequestParam(value = "page", required = false) Integer page,
					   Model model) {
		int pageNum = page != null ? page : DEFAULT_PAGE_NUM;
		Page<Course> paging = courseService.findAll(pageNum, DEFAULT_PAGE_SIZE);
		model.addAttribute("defaultPageSize",DEFAULT_PAGE_SIZE);


		model.addAttribute("page", paging);
		return "home";
	}

	@RequestMapping(value = "/static/{key}")
	public String staticController(@PathVariable(value = "key") String key,
								   Model model) {

		return "/static/" + key;
	}

}
