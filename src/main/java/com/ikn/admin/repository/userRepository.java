package com.ikn.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikn.admin.entity.user;

@Repository
public interface userRepository  extends JpaRepository<user, String>{

	Optional<user> findByEmail(String email);

	boolean existsByEmail(String email);

	void deleteByEmail(String email);


}
