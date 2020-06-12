package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Address;
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


    public Long findByAddress(String addr){
        return aroundRepository.findByAddress(addr);
    }

    public Around findTop1ByAddress(String address){
        return aroundRepository.findTop1ByAddress(address);
    }
}
