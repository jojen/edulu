package org.jojen.wikistudy.controller;

import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.repository.LessonRepository;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LessonService;
import org.jojen.wikistudy.util.RepositoryRefresher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Inject
	protected CourseRepository courseRepository;

	@Inject
	protected LessonRepository lessonRepository;

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public String refresh(){
		RepositoryRefresher.refresh(courseRepository,lessonRepository);
		return "redirect:/";
	}
}
