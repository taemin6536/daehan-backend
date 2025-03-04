package com.daehan.back.user.infra.repository;

import com.daehan.back.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
