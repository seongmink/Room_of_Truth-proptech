package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Contract;
import jdk.nashorn.internal.ir.CallNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findAllByAroundIdAndFloorAndHo(long aroundId, String floor, String ho);

    List<Contract> findTop100AllByLicenseOrderByContractDate(String license);

    @Query(value = "select * from contract where contract_id >= ?1 AND contract_id <= ?2", nativeQuery = true)
    List<Contract> dataTransfer(int start, int end);

    @Query(value = "select license from agent where user_num = ?1", nativeQuery = true)
    String getAgentLicense(Long user_id);

    @Query(value = "SELECT * " +
            "FROM contract " +
            "WHERE around_around_id = ?1 " +
            "order by contract_date desc limit 1 ", nativeQuery = true)
    Contract findOneByAround(Long aroundId);
}
