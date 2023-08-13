package com.ss.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ss.entity.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>
{

	@Query("select new UserEntity(u.email,u.firstName,u.lastName,u.password,u.role) from UserEntity u where u.email=?1")
	public UserEntity findByUserName(String email);
	
	@Query("SELECT u FROM UserEntity u WHERE u.role LIKE CONCAT('%',:search,'%')Or u.email LIKE CONCAT('%',:search,'%')Or u.firstName LIKE CONCAT('%',:search,'%')Or u.lastName LIKE CONCAT('%',:search,'%')")
	public Page<UserEntity> search (String search, Pageable p);

	public List<UserEntity> findByRole(String string);

	public List<UserEntity> findByPasswordUpdated( Date date);

	public UserEntity findByEmail(String requestEmail);


}
