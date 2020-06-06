package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Contract;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findDistinctByLatitudeAndLongitude(String latitude, String longitude);
    List<Contract> findAllByAddressAndFloorAndHo(String address, String floor, String ho);
    List<Contract> findAllByLicense(String license);
    List<Contract> findTop1000AllByLicenseOrderByContractDate(String license);

    Contract getTop1AllByAddressOrderByContractDateDesc(String address);

    @Query(value = "select image " +
            "from contract " +
            "where address= ?1 and floor= ?2 and ho= ?3 " +
            "order by contract_id desc limit 1", nativeQuery = true)
    String getContractImage(String address, String floor, String ho);

    List<Contract> findAllByAddressContaining(String key);

}
