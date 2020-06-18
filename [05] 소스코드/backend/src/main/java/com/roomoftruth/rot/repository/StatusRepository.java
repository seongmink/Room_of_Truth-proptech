package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findTop100AllByLicenseOrderByStartDate(String license);
    List<Status> findAllByAroundIdAndFloorAndHo(long aroundId, String floor, String ho);

    @Query(value = "select * " +
            "from status " +
            "where around_around_id IN (select around_id from around " +
            "where address like ?1% ) " +
            "group by around_around_id", nativeQuery = true)
    List<Status> findAllStatusByCity(String key);

//    @Query(value = "select image " +
//            "from status " +
//            "where address= ?1 and floor= ?2 and ho= ?3 " +
//            "order by status_id desc limit 1", nativeQuery = true)
//    String getStatusImage(String address, String floor, String ho);

}
