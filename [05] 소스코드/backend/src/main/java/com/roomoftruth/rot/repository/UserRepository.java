package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByNum(long num);

	Long save(UserResponseDto entity);

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
