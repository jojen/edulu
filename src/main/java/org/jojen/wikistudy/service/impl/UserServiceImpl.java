package org.jojen.wikistudy.service.impl;

import org.jojen.wikistudy.entity.User;
import org.jojen.wikistudy.repository.UserRepository;
import org.jojen.wikistudy.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    protected UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(int page, int size) {
        Pageable pageable = new PageRequest(page, size, new Sort(
                Direction.DESC, "id"));
        Page<User> lessons = userRepository.findAll(pageable);
        return lessons;
    }

    @Override
    public User findByName(String email) {
        return userRepository.findByName(email);
    }


    @Override
    @Transactional(readOnly = true)
    public User findById(Integer id) {
        User lesson = userRepository.findOne(id);
        return lesson;
    }

    @Override
    @Transactional
    public User insert(User lesson) {
        return userRepository.save(lesson);
    }

    @Override
    @Transactional
    public User update(User lesson) {
        return userRepository.save(lesson);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        userRepository.delete(id);
    }

}
