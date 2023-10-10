package com.urubu.pix.repositories;

import com.urubu.pix.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByCpf(String cpf);

   User findUserById(Long id);

}
