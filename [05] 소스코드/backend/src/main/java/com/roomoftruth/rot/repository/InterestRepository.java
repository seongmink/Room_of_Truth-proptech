package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long>{

	Interest findByUser(User user);

}
