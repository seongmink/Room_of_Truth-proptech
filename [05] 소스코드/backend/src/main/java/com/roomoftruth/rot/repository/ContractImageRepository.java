package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.record.ContractImageRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractImageRepository extends JpaRepository<ContractImageRequestDto, Long> {

    @Query(value="select contract_id, image from ( " +
            "select (select address from around where around_id=around_around_id) as address, contract_id, image from contract " +
            ")as mm " +
            "where address like ?1% ", nativeQuery = true)
    List<ContractImageRequestDto> findAllContractImages(String key);

    @Query(value="select status_id, image from ( " +
            "select (select address from around where around_id=around_around_id) as address, status_id, image from status " +
            ")as mm " +
            "where address like ?1% ", nativeQuery = true)
    List<ContractImageRequestDto> findAllStatusImages(String key);

}
