package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.Group;

public interface GroupRepository extends JpaRepository<Group,Long> {
	List<Group> findByGroupNameContaining(String groupName);
}
