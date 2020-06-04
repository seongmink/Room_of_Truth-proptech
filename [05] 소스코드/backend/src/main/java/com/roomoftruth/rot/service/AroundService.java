package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.repository.AroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AroundService {

    private final AroundRepository aroundRepository;

    public List<Around> findAllAddress(String key){
        return aroundRepository.findByAddressContaining(key);
    }
}
