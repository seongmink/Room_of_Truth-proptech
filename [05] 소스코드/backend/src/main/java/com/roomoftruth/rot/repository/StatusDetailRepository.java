package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.contracts.StatusDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusDetailRepository extends JpaRepository<StatusDetailResponseDto, Long> {

    @Query(nativeQuery = true, value = "SELECT status_id, around_around_id as around_id, address, longitude, latitude, floor, ho, category, detail, cost, license, image, " +
            "ifnull((select AVG(score) as score from favorite where around_around_id = ?1), 0) as is_like, " +
            "start_date, end_date, created_at, is_expired " +
            "FROM status s " +
            "JOIN around a ON s.around_around_id = a.around_id " +
            "WHERE around_id = ?1 AND floor = ?2 AND ho = ?3 ")
    List<StatusDetailResponseDto> findAllStatusByAroundAndFloorAndHo(long aroundId, String floor, String ho);
}
