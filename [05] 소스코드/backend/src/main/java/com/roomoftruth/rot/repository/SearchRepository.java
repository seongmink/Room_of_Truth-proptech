package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<User, Long>{

	List<Search> findByNum(long num);

}
