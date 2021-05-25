package com.signinsignup.plug.sso.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.signinsignup.plug.sso.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	Users findByEmail(String email);
	
	boolean existsByEmail(String email);

	Users findByUsername(String username);

}
