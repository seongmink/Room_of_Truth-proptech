package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByNum(long num);

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
