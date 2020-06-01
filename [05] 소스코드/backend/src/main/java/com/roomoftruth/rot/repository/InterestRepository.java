package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Interest;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.InterestSaveRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<User, Long>{

	Optional<Interest> findByNum(long num);

	Long save(InterestSaveRequestDto requestDto);

}
