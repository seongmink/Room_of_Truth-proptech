package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.contracts.ContractImageRequestDto;
import com.roomoftruth.rot.dto.contracts.ContractSearchResponseDto;
import com.roomoftruth.rot.dto.contracts.StatusDetailResponseDto;
import com.roomoftruth.rot.repository.ContractImageRepository;
import com.roomoftruth.rot.repository.ContractSearchRepository;
import com.roomoftruth.rot.repository.StatusDetailRepository;
import com.roomoftruth.rot.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRespository;
    private final ContractSearchRepository contractSearchRepository;
    private final ContractImageRepository contractImageRepository;
    private final StatusDetailRepository statusDetailRepository;

    /**
     *  1. 상태 이력 등록하기
     * 	saveStatus(StatusSaveRequestDto statusSaveRequestDto)
     */
    @Transactional
    public long saveStatus(Status status) {
        return statusRespository.save(status).getStatusId();
    }


    /**
     * 2. ID로 Status 찾기
     * @param num
     * @return
     */
    public Status findById(long num) {
        Status status = statusRespository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 이력이 없습니다. statusId =" + num));
        return status;
    }

    /**
     * 3. 도시의 모든 건물당 이력 찾기
     * @param key
     * @return
     */
    public List<ContractSearchResponseDto> findAllStatusByCity(String key){
        return contractSearchRepository.findAllStatusByCity(key);
    }

    /**
     * 4. 도시의 모든 사진 찾아오기
     * @param key
     * @return
     */
    public List<ContractImageRequestDto> findStatusImages(String key){
        return contractImageRepository.findAllStatusImages(key);
    }

    /**
     * 5. 상세 이력 찾기
     * @param aroundId
     * @param floor
     * @param ho
     * @return
     */
    public List<StatusDetailResponseDto> findAllStatusByAroundAndFloorAndHo(long aroundId, String floor, String ho){
        return statusDetailRepository.findAllStatusByAroundAndFloorAndHo(aroundId, floor, ho);
    }

}
