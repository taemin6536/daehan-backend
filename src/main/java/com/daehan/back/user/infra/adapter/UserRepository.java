package com.daehan.back.user.infra.adapter;

import com.daehan.back.user.domain.model.User;
import com.daehan.back.user.infra.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserJpaRepository userJpaRepository;

    public User save(final User user){
        return userJpaRepository.save(user);
    }

}
