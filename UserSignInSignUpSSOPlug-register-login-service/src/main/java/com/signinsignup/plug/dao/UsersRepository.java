package com.signinsignup.plug.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.signinsignup.plug.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Users findByEmail(String email);
	
	boolean existsByEmail(String email);

	Users findByUsername(String username);

}
