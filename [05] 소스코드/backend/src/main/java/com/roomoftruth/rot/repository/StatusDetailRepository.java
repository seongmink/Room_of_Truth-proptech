package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.contracts.StatusDetailResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusDetailRepository extends JpaRepository<StatusDetailResponseDto, Long> {

    @Query(nativeQuery = true, value = "select status_id, around_around_id as around_id, address, " +
            "floor, ho, category, detail, cost, license, image, start_date, end_date, created_at, is_expired, IFNULL((select AVG(score) as score from favorite where around_around_id = ?1), 0) as is_like from ( " +
            "select (select address from around where around_id=around_around_id) as address, status_id, around_around_id, floor, ho, category, detail, cost, license, " +
            "image, start_date, end_date, created_at, is_expired from status " +
            ")as mm " +
            "where around_around_id = ?1 AND floor = ?2 AND ho = ?3 ")
    List<StatusDetailResponseDto> findAllStatusByAroundAndFloorAndHo(long aroundId, String floor, String ho);
}
