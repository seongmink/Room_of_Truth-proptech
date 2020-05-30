package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;

import javax.transaction.Transactional;

public interface IUserService {

	@Transactional
	UserResponseDto findByNum(long num);

//	@Transactional
//	Long save(UserResponseDto entity);

	
//	List<User> getAllUser();
// getUserByNum(long num);
//
//	@Transactional
//	int createUser(User user);
//
//	@Transactional
//	int updateEntered(User user);
//
//	@Transactional
//	int obtainAuth(long num);
//
//	@Transactional
//	int updateUser(User user);
//
//	@Transactional
//	int loseAuth(long num);
//	User

}
