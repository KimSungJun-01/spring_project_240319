package com.market.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.market.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findById(int id);
	public UserEntity findByLoginId(String loginId);
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}
