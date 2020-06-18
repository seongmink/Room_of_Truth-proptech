package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findTop100AllByLicenseOrderByStartDate(String license);
    List<Status> findAllByAroundIdAndFloorAndHo(long aroundId, String floor, String ho);

    @Query(value = "select * " +
            "from status s join around a " +
            "on s.around_around_id = a.around_id " +
            "where a.address like ?1% " +
            "group by around_around_id ", nativeQuery = true)
    List<Status> findAllStatusByCity(String key);

    @Query(value = "SELECT * " +
            "FROM status " +
            "WHERE around_around_id = ?1 " +
            "order by start_date desc limit 1", nativeQuery = true)
    Status findOneByAround(Long aroundId);

}
