package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>{

    @Query(nativeQuery = true, value = "SELECT address_id, road_address FROM address WHERE road_address LIKE concat('%', :keyword, '%') ORDER BY instr(road_address, :keyword) limit 5;")
    List<Address> findSimilarAddress(@Param("keyword") String keyword);

}
