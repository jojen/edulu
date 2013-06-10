package org.jojen.wikistudy.repository;

import org.jojen.wikistudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByName(String name);
}
