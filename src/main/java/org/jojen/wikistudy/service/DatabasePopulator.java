package org.jojen.wikistudy.service;

import org.jojen.wikistudy.repository.UserRepository;
import org.jojen.wikistudy.domain.Course;
import org.jojen.wikistudy.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired CourseRepository courseRepository;
    @Autowired Neo4jOperations template;


    private final static Logger log = LoggerFactory.getLogger(DatabasePopulator.class);

    @Transactional
    public List<Course> populateDatabase() {

        List<String> ids = asList("Einführungskurs","Erweiterungskurs","Fortgeschritten");
        List<Course> result=new ArrayList<Course>(ids.size());

        for (String id : ids) {
			Course c = new Course(id,"Title - "+id);
			c.setDescription("Description - "+id);
            result.add(c);
			courseRepository.save(c);
        }


        return result;
    }

    @Transactional
    public void cleanDb() {
        new Neo4jDatabaseCleaner(template).cleanDb();
    }
}
