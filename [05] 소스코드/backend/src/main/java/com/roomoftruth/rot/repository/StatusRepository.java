package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findTop100AllByLicenseOrderByStartDate(String license);

}
