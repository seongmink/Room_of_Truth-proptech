package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.record.ContractSearchResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractSearchRepository extends JpaRepository<ContractSearchResponseDto, Long> {

    @Query(value = "select around_id, latitude, longitude " +
            "from contract c join around a " +
            "on c.around_around_id = a.around_id " +
            "where a.address like ?1% " +
            "group by around_around_id", nativeQuery = true)
    List<ContractSearchResponseDto> findAllContractByCity(String key);

    @Query(value = "select around_id, latitude, longitude " +
            "from status s join around a " +
            "on s.around_around_id = a.around_id " +
            "where a.address like ?1% " +
            "group by around_around_id", nativeQuery = true)
    List<ContractSearchResponseDto> findAllStatusByCity(String key);
}
