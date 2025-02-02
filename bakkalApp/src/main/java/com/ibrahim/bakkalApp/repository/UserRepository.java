package com.ibrahim.bakkalApp.repository;

import com.ibrahim.bakkalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);  // Kullanıcı adına göre kullanıcıyı bulma
}
