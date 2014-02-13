package org.jojen.wikistudy.controller;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.service.BackupService;
import org.jojen.wikistudy.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {



	@Inject
	protected CourseService courseService;

	@Inject
	protected BackupService backupService;

	private static final Logger log = LoggerFactory
											  .getLogger(HomeController.class);


	@RequestMapping(value = "/")
	public String home(@RequestParam(value = "page", required = false) Integer page,
					   Model model) {
		int pageNum = page != null ? page : 0;
		Page<Course> paging = courseService.findAll(pageNum, courseService.DEFAULT_PAGE_SIZE);
		model.addAttribute("defaultPageSize",courseService.DEFAULT_PAGE_SIZE);


		model.addAttribute("page", paging);
		return "home";
	}

	@RequestMapping(value = "/static/{key}")
	public String staticController(@PathVariable(value = "key") String key,
								   Model model) {

		return "/static/" + key;
	}

	@RequestMapping(value = "/backup.zip", method = RequestMethod.GET)
	public void getAllBlobsZip(HttpServletResponse response) {
		File f = null;
		try {
			f = backupService.getBackup();
			response.setContentType("application/zip");
			response.setContentLength((int) (f.length() + 0));

			FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
		} catch (Exception e) {
			// dann gehts nicht
		} finally {
			if (f != null) {
				f.delete();
			}
		}

	}


}
