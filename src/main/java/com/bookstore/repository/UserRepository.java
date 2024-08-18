package com.bookstore.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	List<User> findByGroupId(Long groupId);
	Optional<User> findUserByUsername(String username);
	
	List<User>findUsersByRolesId(Long roleId);
	
	@Transactional
	void deleteByGroupId(long groupId);
}
