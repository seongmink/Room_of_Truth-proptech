package com.rot.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rot.dao.IUserDao;
import com.rot.model.User;

@Repository
public class UserDao implements IUserDao {

//	private String ns = "user.";
//
//	@Autowired
//	private SqlSession sqlSession;
//
//	@Override
//	public List<User> getAllUser() {
//		return sqlSession.selectList(ns + "getAllUser");
//	}
//
//	@Override
//	public User getUserByNum(long num) {
//		return sqlSession.selectOne(ns + "getUserByNum", num);
//	}
//
//	@Override
//	public int createUser(User user) {
//		return sqlSession.insert(ns + "createUser", user);
//	}
//
//	@Override
//	public int updateEntered(User user) {
//		return sqlSession.update(ns + "updateEntered", user);
//	}
//
//	@Override
//	public int obtainAuth(long num) {
//		return sqlSession.update(ns + "obtainAuth", num);
//	}
//
//	@Override
//	public int updateUser(User user) {
//		return sqlSession.update(ns + "updateUser", user);
//	}
//
//	@Override
//	public int loseAuth(long num) {
//		return sqlSession.update(ns + "loseAuth", num);
//	}

}
