package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;
import com.roomoftruth.rot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserResponseDto findByNum(long num) {


		User entity = userRepository.findByNum(num)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
		System.out.println(num);

		System.out.println("entity = " + entity);

		return new UserResponseDto(entity);
	}

//	@Override
//	public Long save(UserResponseDto entity) {
//		userRepository.save(entity);
//	}

	
//	@Autowired
//	private IUserDao userDao;
//
//	@Override
//	public List<User> getAllUser() {
//		return userDao.getAllUser();
//	}
//
//	@Override
//	public User getUserByNum(long num) {
//		return userDao.getUserByNum(num);
//	}
//
//	@Override
//	public int createUser(User user) {
//		return userDao.createUser(user);
//	}
//
//	@Override
//	public int updateEntered(User user) {
//		return userDao.updateEntered(user);
//	}
//
//	@Override
//	public int obtainAuth(long num) {
//		return userDao.obtainAuth(num);
//	}
//
//	@Override
//	public int updateUser(User user) {
//		return userDao.updateUser(user);
//	}
//
//	@Override
//	public int loseAuth(long num) {
//		return userDao.loseAuth(num);
//	}
}
