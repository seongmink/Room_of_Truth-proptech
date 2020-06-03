package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long>{

//    @Query(nativeQuery = true, value = )
//    select u.num, u.nickname as name, a.name as agentName, u.picture as uPicture, a.picture as aPicture, a.point, rank() over (order by point desc) as rnk
//    from user u, agent a
//    where u.num = a.num
//    order by
//    point desc limit 9
//    List<Agent> findAgent();
}
