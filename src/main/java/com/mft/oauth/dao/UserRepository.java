package com.mft.oauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mft.oauth.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select e from User e where e.userName=:userName")
	User findByUserName(@Param("userName") String userName);
}
