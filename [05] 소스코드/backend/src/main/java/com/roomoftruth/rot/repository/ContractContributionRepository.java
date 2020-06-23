package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.ContributionContractResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractContributionRepository extends JpaRepository<ContributionContractResponseDto, Long> {
    @Query(nativeQuery = true, value = "SELECT contract_id, around_id, address, floor, ho, image, 0 as type, created_at, license " +
            "FROM around a JOIN contract c " +
            "WHERE a.around_id = c.around_around_id AND license = (select license from agent where user_num = ?1 ) " +
            "ORDER BY created_at desc limit 100 ")
    List<ContributionContractResponseDto> findByLicense(long userNum);
}
