package org.jojen.wikistudy.service;

import org.jojen.wikistudy.domain.Container;
import org.jojen.wikistudy.domain.Course;
import org.jojen.wikistudy.repository.ContainerRepository;
import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author mh
 * @since 04.03.11
 */
@Service
public class DatabasePopulator {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	ContainerRepository containerRepository;
	@Autowired
	Neo4jOperations template;


	private final static Logger log = LoggerFactory.getLogger(DatabasePopulator.class);

	@Transactional
	public void populateDatabase() {

		List<String> titles = asList("Einführungskurs", "Erweiterungskurs", "Fortgeschritten");
		Container container = new Container("index");


		for (String title : titles) {
			Course c = new Course();
			Course old = new Course();
			old.setTitle("old");
			c.setDescription("Description - " + title);
			c.setTitle("Title - " + title);
			c.setOldVersion(old);
			courseRepository.save(old);
			courseRepository.save(c);
			container.addContent(c);
		}
		containerRepository.save(container);


	}

	@Transactional
	public void cleanDb() {
		new Neo4jDatabaseCleaner(template).cleanDb();
	}
}
