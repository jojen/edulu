package org.jojen.wikistudy.controller;


import org.jojen.wikistudy.domain.Container;
import org.jojen.wikistudy.domain.Course;
import org.jojen.wikistudy.domain.User;
import org.jojen.wikistudy.repository.ContainerRepository;
import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.repository.UserRepository;
import org.jojen.wikistudy.service.DatabasePopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mh
 * @since 04.03.11
 */
@Controller
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	Neo4jOperations template;
	@Autowired
	private DatabasePopulator populator;
	@Autowired
	private ContainerRepository containerRepository;

	private static final Logger log = LoggerFactory.getLogger(CourseController.class);


	// TODO das nur fü den admin zugänglich machen /admin
	@RequestMapping(value = "/populate", method = RequestMethod.GET)
	public String populateDatabase(Model model) {
		populator.populateDatabase();

		addUser(model);
		return "redirect:/";
	}

	// TODO das nur fü den admin zugänglich machen /admin
	@RequestMapping(value = "/clean", method = RequestMethod.GET)
	public String clean(Model model) {
		populator.cleanDb();
		return "movies/list";
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {

		Container c = null;
		try {
			c = containerRepository.findById("index");
		} catch (Exception e) {
			//dann ahben wir halt keinen Container
		}

		model.addAttribute("self", c);
		addUser(model);
		return "index";
	}

	@RequestMapping(value = "/ajax/course/edit", method = RequestMethod.GET)
	public String getEdit(Model model,
						  @RequestParam(value = "id", required = false) Long id
	) {
		addUser(model);
		if (id != null) {
			model.addAttribute("self", courseRepository.findOne(id));
		}

		return "ajax/course.edit";
	}

	@RequestMapping(value = "/course/update", method = RequestMethod.POST)
	public String updateCourse(Model model,
							   @RequestParam(value = "id", required = false) Long id,
							   @RequestParam(value = "title", required = false) String title,
							   @RequestParam(value = "level", required = false) String level,
							   @RequestParam(value = "description", required = false) String description) {

		// Wir holen uns mal nen kurs
		Course c;
		if (id != null) {
			c = courseRepository.findOne(id);

		} else {
			// Wir können auch selber einen anlegen
			c = new Course();
			// TODO, den müssen wir noch an den container hängen
		}
		if (c != null) {
			// dann mal los ... wir setzen einfach mal die sachen
			if (title != null) {
				c.setTitle(title);
			}
			if (description != null) {
				c.setDescription(description);
			}
		}

		courseRepository.save(c);
		addUser(model);

		return "redirect:/";
	}

	@RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
	public String getCourse(Model model, @PathVariable Long id) {
		Course c = courseRepository.findOne(id);
		model.addAttribute("self", c);
		return "course/course";
	}


	private User addUser(Model model) {
		User user = userRepository.getUserFromSession();
		model.addAttribute("user", user);
		return user;
	}


}
