package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long>{

	Optional<Interest> findByUser(User user);

}
