package com.rot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rot.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

//	public List<User> getAllUser();
//	
//	public User getUserByNum(long num);
//	
//	public int createUser(User user);
//	
//	public int updateEntered(User user);
//	
//	public int obtainAuth(long num);
//	
//	public int updateUser(User user);
//	
//	public int loseAuth(long num);
}
