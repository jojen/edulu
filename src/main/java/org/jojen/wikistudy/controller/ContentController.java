package org.jojen.wikistudy.controller;

import org.jojen.wikistudy.entity.LearnContent;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LearnContentService;
import org.jojen.wikistudy.service.LessonService;
import org.jojen.wikistudy.util.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@RequestMapping("/content")
public class ContentController {
	protected static final int DEFAULT_PAGE_NUM = 0;
	protected static final int DEFAULT_PAGE_SIZE = 5;

	@Inject
	protected CourseService courseService;

	@Inject
	protected LessonService lessonService;

	@Inject
	protected LearnContentService learnContentService;

	protected static final Logger log = LoggerFactory
												.getLogger(ContentController.class);

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editContent(@RequestParam(value = "id", required = false) Integer id,
							  @RequestParam(value = "lessonid", required = false) Integer lessonid,
							  @RequestParam(value = "courseid", required = false) Integer courseid, Model model) {
		if (id == null) {
			model.addAttribute(new LearnContent());
		} else {
			model.addAttribute(learnContentService.findById(id));
		}
		model.addAttribute("lessonid", lessonid);
		model.addAttribute("courseid", courseid);
        model.addAttribute(new FileUpload());

		return "/content/content.edit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editContent(@Valid LearnContent learnContent,
							  BindingResult bindingResult,
							  @RequestParam(value = "id", required = false) Integer id,
							  @RequestParam(value = "lessonid", required = true) Integer lessonid,
							  @RequestParam(value = "courseid", required = true) Integer courseid,
							  Model model) {
		log.debug("edit content={}", learnContent);

		LearnContent modelContent;
		Lesson l = null;
		if (id == null) {
			modelContent = new LearnContent();
			l = lessonService.findById(lessonid);
			l.addContent(modelContent);

		} else {
			modelContent = learnContentService.findById(id);
		}

		// TODO hier vielleicht noch ein bisschen reflections
		modelContent.setName(learnContent.getName());
		modelContent.setText(learnContent.getText());
		learnContentService.update(modelContent);
		if (l != null) {
			lessonService.update(l);
		}
		return "redirect:/course/" + courseid + "/lesson/" + lessonid;
	}


    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String uploadFile(Model model,FileUpload fileUpload) {
         return "json/boolean";
    }


}
