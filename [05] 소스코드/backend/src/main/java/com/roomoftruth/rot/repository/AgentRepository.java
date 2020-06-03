package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long>{

//    List<Agent> findTop9AgentOrderByPointDesc();
    @Query(nativeQuery = true, value = "SELECT agent_id, user_num, name, picture, point, rank() over (order by point desc) as rnk, " +
            "created_at, updated_at, address, count, license, representative FROM agent ORDER BY point DESC")
    List<Agent> getRankingTop9();

    @Modifying
    @Query(value = "update agent set rnk = ?1 where agent_id = ?2", nativeQuery = true)
    void updateRank(int rnk, long id);
}
