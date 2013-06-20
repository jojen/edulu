package org.jojen.wikistudy.util;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.repository.LessonRepository;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jochen
 * Date: 20.06.13
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */

public class RepositoryRefresher {

	public static void refresh(CourseRepository courseRepository, LessonRepository lessonRepository){
		courseRepository.deleteAll();
		lessonRepository.deleteAll();
		List<Course> courses = new ArrayList<Course>();

		int itemCount = 3;

		for (int i = 1; i <= itemCount; i++) {
			Course c = new Course();
			c.setName("Course - " + i);
			c.setDescription("Description - " + i);
			courses.add(c);
		}
		Course course = courses.get(0);
		Lesson l = new Lesson();
		course.addLessons(l);

		lessonRepository.save(l);
		courseRepository.save(courses);
	}
}
