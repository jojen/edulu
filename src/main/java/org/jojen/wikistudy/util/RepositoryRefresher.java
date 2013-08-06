package org.jojen.wikistudy.util;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.entity.Text;
import org.jojen.wikistudy.repository.ContentRepository;
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

	public static void refresh(CourseRepository courseRepository, LessonRepository lessonRepository, ContentRepository contentRepository){
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


		Text t1 = new Text();
		t1.setName("First Text Module");
		t1.setText("<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet</p><p>http://www.youtube.com/watch?v=nxnDkVRpBi4&list=PL22FB36EAEA0D2DF0</p>");

		Text t2 = new Text();
		t2.setName("Second Text Module");
		t2.setText("<p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi</p>");

		l.addContent(t1);
		l.addContent(t2);

		contentRepository.save(t1);
		contentRepository.save(t2);
		lessonRepository.save(l);
		courseRepository.save(courses);
	}
}
