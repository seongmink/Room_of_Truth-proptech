package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByNum(long num);

}
