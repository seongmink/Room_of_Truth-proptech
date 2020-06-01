package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.ContractResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRespository extends JpaRepository<Contract, Long> {
    List<Contract> findDistinctByLatitudeAndLongitude(String latitude, String longitude);
    List<Contract> findAllByAddressAndFloorAndHo(String address, String floor, String ho);
    List<Contract> findAllByLicense(String license);

//    @Query("select distinct address, dong, ho ,longitude, latitude from(JPAExpressions select distinct address, dong, ho ,longitude, latitude from building union select distinct address,dong,ho,longitude, latitude  from maintenance) as main  where latitude= :latitude and longitude= :longitude order by dong asc, ho asc")
//    List<Contract> findAllDetails(@Param("latitude") String latitude,
//                                  @Param("longitude") String longitude);

}
