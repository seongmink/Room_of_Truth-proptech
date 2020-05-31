package com.roomoftruth.rot.service;

import com.roomoftruth.rot.dto.UserInterestUpdateRequestDto;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.dto.UserSaveRequestDto;
import com.roomoftruth.rot.dto.UserInfoUpdateRequestDto;

import javax.transaction.Transactional;

public interface IUserService {

	@Transactional
	UserResponseDto findByNum(long num);

	@Transactional
	Long save(UserSaveRequestDto requestDto);

	@Transactional
	Long save(UserInfoUpdateRequestDto requestDto);

	@Transactional
	Long save(UserInterestUpdateRequestDto requestDto);

	
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
