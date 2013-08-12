package org.jojen.wikistudy.util;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.entity.Text;
import org.jojen.wikistudy.service.ContentService;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LessonService;


/**
 * Created with IntelliJ IDEA.
 * User: jochen
 * Date: 20.06.13
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */

public class RepositoryRefresher {

	public static void refresh(CourseService courseService, LessonService lessonService, ContentService contentService) {
		courseService.deleteAll();
		lessonService.deleteAll();
		contentService.deleteAll();


		Course course = new Course();
		course.setName("Test Course");
		course.setDescription("This is only a dummy Course, prease edit or delete. Feel free");


		Lesson l = new Lesson();
		course.addLessons(l);


		Text t1 = new Text();
		t1.setName("First Text Module");
		t1.setText("<p>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet</p><p>http://www.youtube.com/watch?v=nxnDkVRpBi4&list=PL22FB36EAEA0D2DF0</p>");

		Text t2 = new Text();
		t2.setName("Second Text Module");
		t2.setText("<p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi</p>");

		l.addContent(t1);
		contentService.add(t1, l);

		l.addContent(t2);
		contentService.add(t2, l);

		lessonService.add(l, course);

		courseService.update(course);
	}
}
