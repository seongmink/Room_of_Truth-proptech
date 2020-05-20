package com.rot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rot.dao.IUserDao;
import com.rot.model.User;
import com.rot.service.IUserService;

@Service
public class UserService implements IUserService {

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
