package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.ContributionStatusResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusContributionRepository extends JpaRepository<ContributionStatusResponseDto, Long> {
    @Query(nativeQuery = true, value = "SELECT status_id, address, floor, ho, image, 2 as type, created_at, license " +
            "FROM around a JOIN status s " +
            "WHERE a.around_id = s.around_around_id AND license = (select license from agent where user_num = ?1 ) " +
            "ORDER BY created_at desc limit 100 ")
    List<ContributionStatusResponseDto> findByLicense(long userNum);
}
