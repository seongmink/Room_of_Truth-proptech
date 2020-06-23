package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.contracts.ContractDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractDetailRepository extends JpaRepository<ContractDetailResponseDto, Long> {

    @Query(nativeQuery = true, value = "SELECT contract_id, around_around_id as around_id, address, longitude, latitude, exclusive, floor, ho, kind, detail, cost, monthly, license, image, " +
            "ifnull((select AVG(score) as score from favorite where around_around_id = ?1), 0) as is_like, " +
            "contract_date, created_at, is_expired " +
            "FROM contract c " +
            "JOIN around a ON c.around_around_id = a.around_id " +
            "WHERE around_id = ?1 AND floor = ?2 AND ho = ?3")
    List<ContractDetailResponseDto> findAllContractsByAroundAndFloorAndHo(Long aroundId, String floor, String ho);
}
