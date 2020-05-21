package com.rot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rot.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long>{

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
