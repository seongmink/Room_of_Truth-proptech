package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.contracts.ContractConfirmResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContractConfirmRepository extends JpaRepository<ContractConfirmResponseDto, Long> {

    @Query(value = "select contract_id, around_around_id, exclusive, floor, ho, kind, detail " +
            ",cost, monthly, license, image, contract_date, created_at, is_expired, " +
            "(select address from around where around_id = around_around_id) as address " +
            "from contract " +
            "where contract_id = ?1 ", nativeQuery = true)
    ContractConfirmResponseDto findConfirmById(long num);
}
