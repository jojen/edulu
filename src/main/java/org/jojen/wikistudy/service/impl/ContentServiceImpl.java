package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.Blobbased;
import org.jojen.wikistudy.entity.Content;
import org.jojen.wikistudy.entity.Lesson;
import org.jojen.wikistudy.repository.ContentRepository;
import org.jojen.wikistudy.service.BlobService;
import org.jojen.wikistudy.service.ContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class ContentServiceImpl implements ContentService {
	@Inject
	protected ContentRepository contentRepository;

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
		contentRepository.delete(id);
	}

	@Override
	@Transactional
	public void deleteAll() {
		contentRepository.deleteAll();
	}

	@Override
	@Transactional
	public void add(Content c, Lesson l) {
		c.setPosition(l.getContent().size());
		contentRepository.save(c);
	}


}
