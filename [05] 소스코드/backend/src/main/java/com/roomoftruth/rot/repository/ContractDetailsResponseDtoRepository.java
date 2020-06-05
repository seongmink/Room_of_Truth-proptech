package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.ContractDetailsResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractDetailsResponseDtoRepository extends JpaRepository<ContractDetailsResponseDto, Long> {

    @Query(value = "select contract_id, address, floor, ho, latitude, longitude, image, contract_date " +
            "from (select distinct contract_id, address, floor, ho, latitude, longitude, image, contract_date " +
            "from contract " +
            "union " +
            "select distinct status_id, address, floor, ho, latitude, longitude, image, report_date " +
            "from status)as main " +
            "where latitude = ?1 AND longitude = ?2 " +
            "order by contract_date desc", nativeQuery = true)
    List<ContractDetailsResponseDto> findAllDetail(String latitude, String longitude);

}
