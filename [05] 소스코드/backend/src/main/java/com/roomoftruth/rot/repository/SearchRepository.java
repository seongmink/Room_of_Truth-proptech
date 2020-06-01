package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long>{

	List<Search> findByNum(long num);

	Search findByNumAndKeyword(long num, String keyword);

}
