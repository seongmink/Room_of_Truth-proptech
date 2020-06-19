package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.contracts.ContractListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ContractListRepository extends JpaRepository<ContractListResponseDto, Long> {

    @Query(value = "select contract_id, around_around_id as around_id, address, floor, ho " +
            "from ( " +
            "select contract_id, (select address from around where around_id = around_around_id) as address,floor, ho, around_around_id from contract " +
            "where around_around_id IN " +
            "(select around_id from around where address like ?1%)" +
            "union " +
            "select status_id, (select address from around where around_id = around_around_id) as address,floor, ho, around_around_id from status " +
            "where around_around_id IN " +
            "(select around_id from around where address like ?1%)) as main " +
            "order by floor, ho asc", nativeQuery = true)
    List<ContractListResponseDto> findAllContractsList(String key);

}