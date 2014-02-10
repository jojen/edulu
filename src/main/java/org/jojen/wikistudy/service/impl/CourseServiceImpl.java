package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.controller.HomeController;
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
import java.util.Collections;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
	@Inject
	protected CourseRepository courseRepository;
	@Inject
	LessonService lessonService;

	@Override
	@Transactional(readOnly = true)
	public Page<Course> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(Direction.ASC, "position"));
		Page<Course> persons = courseRepository.findAll(pageable);
		return persons;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Course> findAll() {
		Pageable pageable = new PageRequest(0, getSize(), new Sort(Direction.ASC, "position"));
		Page<Course> page = courseRepository.findAll(pageable);
		return new ArrayList<Course>(page.getContent());
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
		if (c != null) {
			ArrayList<Lesson> list = new ArrayList<Lesson>(c.getLessons());

			for (Lesson l : list) {
				lessonService.delete(l, c);
			}
			c.getLessons().clear();
			moveTotal(c.getPosition()-1,getSize()-1);
			courseRepository.delete(id);
		}
	}

	@Override
	@Transactional
	public void deleteAll() {
		courseRepository.deleteAll();
	}

	@Override
	public int getSize() {
		return courseRepository.findAll().size();
	}

	public void moveTotal(Integer from, Integer to) {
		if (from.equals(to)) {
			return;
		}

		List<Course> list = findAll();

		// Warum auch immer klappt das verschieben nur in eine Richtung
		if (from > to) {
			Collections.reverse(list);
			Collections.rotate(list.subList(list.size() - 1 - from, list.size() - to), -1);
			Collections.reverse(list);
		} else {
			Collections.rotate(list.subList(from, to + 1), -1);
		}


		// Wir updaten noch alle positionen

		int i = 1;
		for (Course c : list) {
			c.setPosition(i);
			update(c);
			i++;
		}
	}

	@Override
	public void move(Integer page, Integer from, Integer to) {
		if (page == null) {
			page = 0;
		}

		if (from.equals(to)) {
			return;
		}

		List<Course> list = new ArrayList<Course>(findAll(page, DEFAULT_PAGE_SIZE).getContent());


		// Warum auch immer klappt das verschieben nur in eine Richtung
		if (from > to) {
			Collections.reverse(list);
			Collections.rotate(list.subList(list.size() - 1 - from, list.size() - to), -1);
			Collections.reverse(list);
		} else {
			Collections.rotate(list.subList(from, to + 1), -1);
		}


		// Wir updaten noch alle positionen

		int i = 1;
		for (Course c : list) {
			c.setPosition(page * DEFAULT_PAGE_SIZE + i);
			update(c);
			i++;
		}


	}

}
