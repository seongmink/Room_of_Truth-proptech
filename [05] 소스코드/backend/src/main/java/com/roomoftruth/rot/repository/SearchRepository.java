package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Search;
import com.roomoftruth.rot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long>{

	List<Search> findByUser(User user);

	Search findByUserAndKeyword(User user, String keyword);

//	@Query("SELECT roadAddress FROM address a")
//	@Query("select num, roadaddress from address where roadaddress like concat('%', ?1, '%')
//	order by instr(roadaddress, ?1) limit 5;")
//	List<Search> findAddress(String keyword);

}
