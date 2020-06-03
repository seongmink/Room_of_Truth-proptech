package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findDistinctByLatitudeAndLongitude(String latitude, String longitude);
    List<Contract> findAllByAddressAndFloorAndHo(String address, String floor, String ho);
    List<Contract> findAllByLicense(String license);

    @Query(value = "select image " +
            "from contract " +
            "where address= ?1 and floor= ?2 and ho= ?3 " +
            "order by contract_id desc limit 1", nativeQuery = true)
    String getContractImage(String address, String floor, String ho);

//    @Query(value = "select distinct contract_id, address, floor, ho, latitude, longitude from" +
//            "( select distinct contract_id, address, floor, ho, latitude, longitude from contract union select distinct status_id, address, floor, ho, latitude, longitude from status) " +
//            "as main  where latitude= ?1 and longitude= ?2 order by floor asc, ho asc", nativeQuery = true)
//    List<Contract> findAllDetails(String latitude, String longitude);

}
