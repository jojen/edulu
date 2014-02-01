package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.Blobbased;
import org.jojen.wikistudy.entity.Content;
import org.jojen.wikistudy.entity.Course;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.LessonRepository;
import org.jojen.wikistudy.service.BlobService;
import org.jojen.wikistudy.service.ContentService;
import org.jojen.wikistudy.service.CourseService;
import org.jojen.wikistudy.service.LessonService;
import org.jojen.wikistudy.util.jpacloner.JpaCloner;
import org.jojen.wikistudy.util.jpacloner.graphs.PropertyFilter;
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
public class LessonServiceImpl implements LessonService {
	@Inject
	protected LessonRepository lessonRepository;
	@Inject
	protected ContentService contentService;
	@Inject
	protected CourseService courseService;
	@Inject
	protected BlobService blobService;

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
	public void delete(Lesson l, Course c) {
		if (l != null) {
			// wir nehmen uns mal aus dem kurs raus
			c.getLessons().remove(l.getPosition() - 1);
			courseService.update(c);

			ArrayList<Content> list = new ArrayList<Content>(l.getContent());
			l.getContent().clear();
			for (Content content : list) {
				contentService.deleteById(content.getId());
			}
			lessonRepository.delete(l.getId());
			// dann sortieren wir alles neu
			int i = 1;
			for (Lesson lesson : c.getLessons()) {
				lesson.setPosition(i);
				update(lesson);
				i++;
			}
		}

	}

	@Override
	public Integer copy(Lesson l, Course c) {


		Lesson clonel = JpaCloner.clone(l, new PropertyFilter() {
			@Override
			public boolean test(Object entity, String property) {
				// do not clone primary keys for the whole entity subgraph
				return !"id".equals(property);
			}
		});

		int i = 0;
		for (Content content : clonel.getContent()) {
			String blobPath = null;
			Integer originalId = -1;
			if (content instanceof Blobbased) {
				originalId = l.getContent().get(i).getId();
				blobPath = blobService.get(originalId);
			}
			contentService.add(content, clonel);
			if (blobPath != null) {
				blobService.copy(originalId, content.getId());
			}
			i++;
		}
		add(clonel, c);

		return clonel.getId();
	}

	@Override
	@Transactional
	public void deleteAll() {
		lessonRepository.deleteAll();
	}

	@Override
	public void move(Course c, Integer from, Integer to) {
		List<Lesson> list = c.getLessons();

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
		for (Lesson l : list) {
			l.setPosition(i);
			update(l);
			i++;
		}

		courseService.update(c);
	}

	@Override
	@Transactional
	public void add(Lesson l, Course c) {
		l.setPosition(c.getLessons().size());
		lessonRepository.save(l);
		c.addLessons(l);
		courseService.update(c);
	}

}
