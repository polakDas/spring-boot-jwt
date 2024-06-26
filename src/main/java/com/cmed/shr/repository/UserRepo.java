package com.cmed.shr.repository;

import com.cmed.shr.entity.UserEnitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEnitiy, Long> {
    public Optional<UserEnitiy> findByEmail(String email);
}
