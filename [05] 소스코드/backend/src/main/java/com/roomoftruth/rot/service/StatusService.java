package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Status;
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

    /**
     *  1. 상태 이력 등록하기
     * 	saveStatus(StatusSaveRequestDto statusSaveRequestDto)
     *
     */
    @Transactional
    public long saveStatus(Status status) {
        return statusRespository.save(status).getStatusId();
    }


    /**
     *
     * @param num
     * @return
     */
    public Status findById(long num) {
        Status status = statusRespository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("해당 이력이 없습니다. statusId =" + num));
        return status;
    }

    public List<Status> findAllStatusByCity(String key){
        return statusRespository.findAllStatusByCity(key);
    }

    /**
     *  4. latitude, longitude로 건물 정보 찾기 -> 군집 해당하는 목록 모두 (1개씩)
     *  List<Building> getBuildingDetail(Building building);
     *
     */
//    public List<StatusResponseDto> findDistinctByLatitudeAndLongitude(String latitude, String longitude){
//        List<Status> data = statusRespository.findDistinctByLatitudeAndLongitude(latitude, longitude);
//        List<StatusResponseDto> result = new ArrayList<>();
//
//        for (Status status: data) {
//            result.add(new StatusResponseDto(status));
//        }
//
//        return result;
//    }

    /**
     *
     * @param aroundId
     * @param floor
     * @param ho
     * @return
     */
    public List<Status> findAllByAroundIdAndFloorAndHo(long aroundId, String floor, String ho){
        return statusRespository.findAllByAroundIdAndFloorAndHo(aroundId, floor, ho);
    }

    /**
     *  6. status 테이블에서 ID로 이미지 가져오기
     *  String getStatusImage(long statusId);
     *
     */
//    public String getStatusImage(ContractFindResponseDto contractFindResponseDto){
//        return statusRespository.getStatusImage(contractFindResponseDto.getAddress(), contractFindResponseDto.getFloor(), contractFindResponseDto.getHo());
//    }

    /**
     *  7. 공인중개사가 등록한 상태 이력 모두 출력
     *  List<Building> findAllByLicense(String license);
     *
     */
//    public List<StatusResponseDto> findAllByLicense(String license){
//        List<Status> data = statusRespository.findAllByLicense(license);
//        List<StatusResponseDto> result = new ArrayList<>();
//
//        for (Status status : data) {
//            result.add(new StatusResponseDto(status));
//        }
//        return result;
//    }
}
