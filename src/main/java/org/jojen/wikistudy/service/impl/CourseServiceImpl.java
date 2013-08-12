package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.CourseRepository;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LessonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;

@Service
public class CourseServiceImpl implements CourseService {
	@Inject
	protected CourseRepository courseRepository;
	@Inject
	LessonService lessonService;

	@Override
	@Transactional(readOnly = true)
	public Page<Course> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
																		Direction.ASC, "id"));
		Page<Course> persons = courseRepository.findAll(pageable);
		return persons;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Course> findByNameLike(String name, int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
																		Direction.DESC, "id"));
		String q = "%" + name + "%";
		Page<Course> persons = courseRepository.findByNameLike(q, pageable);
		return persons;
	}

	@Override
	@Transactional(readOnly = true)
	public Course findById(Integer id) {
		Course course = courseRepository.findOne(id);
		return course;
	}

	@Override
	@Transactional
	public Course insert(Course course) {
		return courseRepository.save(course);
	}

	@Override
	@Transactional
	public Course update(Course course) {
		return courseRepository.save(course);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		Course c = courseRepository.findOne(id);
		if(c!= null){
			ArrayList<Lesson> list = new ArrayList<Lesson>(c.getLessons());
			c.getLessons().clear();
			for(Lesson l: list){
				lessonService.deleteById(l.getId());
			}
			courseRepository.delete(id);
		}
	}

	@Override
	@Transactional
	public void deleteAll() {
		courseRepository.deleteAll();
	}

}
