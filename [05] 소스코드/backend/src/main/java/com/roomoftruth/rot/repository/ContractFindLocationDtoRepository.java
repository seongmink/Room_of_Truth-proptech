package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.ContractFindLocationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractFindLocationDtoRepository extends JpaRepository<ContractFindLocationDto, String> {
    @Query(value = "select distinct(a.address), c.latitude, c.longitude from contract c join around a on c.address = a.address " +
            "where a.address like %?1%", nativeQuery = true)
    List<ContractFindLocationDto> findContractLocations(String key);

}
