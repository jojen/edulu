package org.jojen.wikistudy.listener;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.repository.LessonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

public class ApplicationInitilizeListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory
												 .getLogger(ApplicationInitilizeListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.debug("initializing..");
		WebApplicationContext ctx = WebApplicationContextUtils
											.getWebApplicationContext(sce.getServletContext());
		CourseRepository courseRepository = ctx.getBean(CourseRepository.class);
		courseRepository.deleteAll();
		LessonRepository lessonRepository = ctx.getBean(LessonRepository.class);
		lessonRepository.deleteAll();
		List<Course> courses = new ArrayList<Course>();

		int itemCount = 3;

		for (int i = 1; i <= itemCount; i++) {
			Course c = new Course();
			c.setName("Course - " + i);
			c.setDescription("Description - " + i);
			courses.add(c);
		}
		Course course = courses.iterator().next();
		Lesson l = new Lesson();
		course.addLessons(l);

		lessonRepository.save(l);
		courseRepository.save(courses);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
