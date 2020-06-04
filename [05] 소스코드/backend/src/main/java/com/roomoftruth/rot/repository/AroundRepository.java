package com.roomoftruth.rot.repository;

import com.roomoftruth.rot.domain.Around;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AroundRepository extends JpaRepository<Around, Long> {

    List<Around> findByAddressContaining(String key);
}
