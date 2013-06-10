package org.jojen.wikistudy.service;

import org.jojen.wikistudy.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> findAll(int page, int size);

    User findByName(String email);

    User findById(Integer id);

    User insert(User user);

    User update(User user);

    void deleteById(Integer id);


}
