package com.daehan.back.user.domain.repository;

import com.daehan.back.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
}
