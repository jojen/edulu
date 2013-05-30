package org.jojen.wikistudy.domain.dep;

import org.jojen.wikistudy.domain.Course;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mh
 * @since 10.11.11
 */
public class Director extends Person {
	public Director(String id, String name) {
		super(id, name);
	}

	public Director() {
	}


	@RelatedTo(elementClass = Course.class, type = "DIRECTED")
	private Set<Course> directedMovies = new HashSet<Course>();

	public Director(String id) {
		super(id, null);
	}

	public Set<Course> getDirectedMovies() {
		return directedMovies;
	}

	public void directed(Course course) {
		this.directedMovies.add(course);
	}

}
