package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.dto.contracts.StatusConfirmResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusConfirmRepository extends JpaRepository<StatusConfirmResponseDto, Long> {
    @Query(value = "select status_id, around_around_id, floor, ho, category, detail " +
            ",cost, license, image, start_date, end_date, created_at, is_expired, " +
            "(select address from around where around_id = around_around_id) as address " +
            "from status " +
            "where status_id = ?1 ", nativeQuery = true)
    StatusConfirmResponseDto findConfirmById(long num);
}
