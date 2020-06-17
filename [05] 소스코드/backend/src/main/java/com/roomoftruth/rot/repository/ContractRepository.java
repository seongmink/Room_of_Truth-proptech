package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Contract;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findTop100AllByLicenseOrderByContractDate(String license);

//    @Query(value = "select image " +
//            "from contract " +
//            "where address= ?1 and floor= ?2 and ho= ?3 " +
//            "order by contract_id desc limit 1", nativeQuery = true)
//    String getContractImage(String address, String floor, String ho);

    @Query(value = "select * " +
            "from contract " +
            "where around_around_id IN (select around_id from around " +
            "where address like ?1% ) " +
            "group by around_around_id", nativeQuery = true)
    List<Contract> findAllContractByCity(String key);

    @Query(value = "select * from contract where contract_id >= ?1 AND contract_id <= ?2", nativeQuery = true)
    List<Contract> dataTransfer(int start, int end);

    @Query(value = "select license from agent where user_num = ?1", nativeQuery = true)
    String getAgentLicense(Long user_id);
}
