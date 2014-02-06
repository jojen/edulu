package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.Blobbased;
import org.jojen.wikistudy.entity.Container;
import org.jojen.wikistudy.entity.Content;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.ContentRepository;
import org.jojen.wikistudy.service.BlobService;
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
import java.util.Collections;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
	@Inject
	protected ContentRepository contentRepository;
	@Inject
	protected LessonService lessonService;

	@Inject
	BlobService blobService;

	@Override
	@Transactional(readOnly = true)
	public Page<Content> findAll(int page, int size) {
		Pageable pageable = new PageRequest(page, size, new Sort(
																		Direction.DESC, "id"));
		Page<Content> learnContent = contentRepository.findAll(pageable);
		return learnContent;
	}


	@Override
	@Transactional(readOnly = true)
	public Content findById(Integer id) {
        Content learnContent = contentRepository.findOne(id);
		return learnContent;
	}

	@Override
	@Transactional
	public Content update(Content learnContent) {
		return contentRepository.save(learnContent);
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		Content c = contentRepository.findOne(id);
		if (c instanceof Blobbased){
			Blobbased b = (Blobbased) c;
			blobService.delete(b.getId());
		}
		if(c instanceof Container){
			Container container = (Container) c;
			if(container.getFirstContent()!=null){
				int c1id = container.getFirstContent().getId();
				container.setFirstContent(null);
				deleteById(c1id);
			}
			if(container.getSecondContent()!=null){
				int c2id = container.getSecondContent().getId();
				container.setSecondContent(null);
				deleteById(c2id);
			}

		}
		contentRepository.delete(id);
	}

	@Override
	@Transactional
	public void deleteAll() {
		contentRepository.deleteAll();
	}



	@Override
	public void move(Lesson l, Integer from, Integer to) {
		if(from.equals(to)){
			return;
		}
		List<Content> list = l.getContent();

		// Warum auch immer klappt das verschieben nur in eine Richtung
		if (from > to) {
			Collections.reverse(list);
			Collections.rotate(list.subList(list.size() - from - 1, list.size() - to), -1);
			Collections.reverse(list);
		} else {
			Collections.rotate(list.subList(from, to + 1), -1);
		}

		// Wir updaten noch alle positionen
		int i = 1;
		for (Content c : list) {
			c.setPosition(i);
			update(c);
			i++;
		}

		lessonService.update(l);

	}






	@Override
	@Transactional
	public void add(Content c, Lesson l) {
		if(l.getContent()==null){
			c.setPosition(0);
		}else{
			c.setPosition(l.getContent().size());
		}

		contentRepository.save(c);
	}


}
