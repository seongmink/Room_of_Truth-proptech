package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long>{

	List<Search> findTop5ByUserOrderByUpdatedAtDesc(User user);

	Search findByUserAndKeyword(User user, String keyword);

}
