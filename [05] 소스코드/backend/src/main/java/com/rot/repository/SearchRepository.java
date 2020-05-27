package com.rot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rot.model.Search;

public interface SearchRepository extends JpaRepository<Search, Integer>{

//	List<Search> getAllSearch(long num);
//
//	Search isSearched(Search search);
//
//	int search(Search search);
//
//	int deleteSearch(Search search);
//
//	int updateSearch(Search search);
}
