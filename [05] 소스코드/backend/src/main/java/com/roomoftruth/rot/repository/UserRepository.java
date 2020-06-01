package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.UserSaveRequestDto;
import com.roomoftruth.rot.dto.UserUpdateRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{


	Optional<User> findByNum(long num);

	Long save(UserSaveRequestDto requestDto); // 로그인 시

	Long save(UserUpdateRequestDto updateRequestDto); // 사용자 정보 업데이트


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
