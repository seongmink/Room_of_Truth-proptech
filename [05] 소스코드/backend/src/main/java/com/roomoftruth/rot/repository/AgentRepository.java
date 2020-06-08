package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgentRepository extends JpaRepository<Agent, Long>{

    @Query(nativeQuery = true, value = "SELECT * FROM agent ORDER BY point DESC")
    List<Agent> getRanking();

    @Query(nativeQuery = true, value = "SELECT agent_id, user_num, name, picture, point, rank() over (ORDER BY point DESC) AS rnk, " +
            "created_at, updated_at, address, count, license, representative FROM agent ORDER BY point DESC")
    List<Agent> getRankingTop9();

    @Query(nativeQuery = true, value = "SELECT * FROM agent WHERE user_num = :num")
    Agent getAgentByUserNum(long num);

    @Modifying
    @Query(nativeQuery = true, value = "update agent set count = count + 1, point = point + 10 where license = ?1")
    void pointUp(String license);

    @Query(nativeQuery = true, value = "SELECT * FROM agent WHERE license = ?1")
    Agent findByLicense(String license);
}
