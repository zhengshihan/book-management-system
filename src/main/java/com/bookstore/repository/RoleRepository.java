package com.bookstore.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.Role;
import com.bookstore.model.User;

public interface RoleRepository extends JpaRepository<Role,Long> {

	List<Role> findRolesByUsersId(Long userId);
}
