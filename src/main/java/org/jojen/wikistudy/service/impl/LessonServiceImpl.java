package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.Content;
import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.LessonRepository;
import org.jojen.wikistudy.service.ContentService;
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
public class LessonServiceImpl implements LessonService {
	@Inject
	protected LessonRepository lessonRepository;
	@Inject
	protected ContentService contentService;

	@Override
	@Transactional(readOnly = true)
	public Page<Lesson> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
																		Direction.DESC, "id"));
		Page<Lesson> lessons = lessonRepository.findAll(pageable);
		return lessons;
	}


	@Override
	@Transactional(readOnly = true)
	public Lesson findById(Integer id) {
		Lesson lesson = lessonRepository.findOne(id);
		return lesson;
	}


	@Override
	@Transactional
	public Lesson update(Lesson lesson) {
		return lessonRepository.save(lesson);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		Lesson l = lessonRepository.findOne(id);
		if(l!= null){
			ArrayList<Content> list = new ArrayList<Content>(l.getContent());
			l.getContent().clear();
			for(Content c:list){
				contentService.deleteById(c.getId());
			}
			lessonRepository.delete(id);
		}
	}

	@Override
	@Transactional
	public void deleteAll() {
		lessonRepository.deleteAll();
	}

	@Override
	@Transactional
	public void add(Lesson l, Course c) {
		l.setPosition(c.getLessons().size());
		lessonRepository.save(l);
	}

}
