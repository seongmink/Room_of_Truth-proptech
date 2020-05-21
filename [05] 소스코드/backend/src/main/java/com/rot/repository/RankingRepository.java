package com.rot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rot.model.Ranking;

public interface RankingRepository extends JpaRepository<Ranking, Long>{
	
//	List<Ranking> getRanking();
}
