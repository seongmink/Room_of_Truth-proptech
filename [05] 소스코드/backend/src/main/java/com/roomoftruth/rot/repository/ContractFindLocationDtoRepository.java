package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.ContractFindLocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractFindLocationDtoRepository extends JpaRepository<ContractFindLocationDto, String> {

    @Query(value = "select address, latitude, longitude from contract where address = ?1 limit 1", nativeQuery = true)
    ContractFindLocationDto findContractLocation(String address);
}
