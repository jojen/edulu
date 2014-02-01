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
	public Integer copy(Lesson l, Course c) {



		Lesson clonel = JpaCloner.clone(l,new PropertyFilter() {
			@Override
			public boolean test(Object entity, String property) {
				// do not clone primary keys for the whole entity subgraph
				return !"id".equals(property);
			}
		});

		int i = 0;
		for(Content content:clonel.getContent()){
			String blobPath = null;
			Integer originalId = -1;
			if(content instanceof Blobbased){
				originalId = l.getContent().get(i).getId();
				blobPath = blobService.get(originalId);
			}
			contentService.add(content, clonel);
			if(blobPath!= null){
				blobService.copy(originalId,content.getId());
			}
			i++;
		}
		add(clonel,c);
		c.addLessons(clonel);


		return clonel.getId();
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
		c.addLessons(l);
		courseService.update(c);
	}

}
