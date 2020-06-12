//package com.roomoftruth.rot.service;
//
//import com.roomoftruth.rot.domain.Status;
//import com.roomoftruth.rot.repository.StatusRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class StatusService {
//
//    private final StatusRepository statusRespository;
//
//    /**
//     *  1. 상태 이력 등록하기
//     * 	saveStatus(StatusSaveRequestDto statusSaveRequestDto)
//     *
//     */
//    @Transactional
//    public long saveStatus(FabricStatusRecord fabricStatusRecord) {
//        System.out.println("0------------ saveStatus 시작 -------------0");
//        Status status = new Status(fabricStatusRecord);
//        System.out.println(status.toString());
//        System.out.println("statusID : " + status.getStatusId());
//        return statusRespository.save(status).getStatusId();
//    }
//
//    /**
//     *  2. 모든 상태 이력 찾아오기
//     * 	public List<Status> findAll()
//     *
//     */
//    public List<Status> findAll(){
//        return statusRespository.findAll();
//    }
//
//    /**
//     *  3. ID로 상태 이력 찾기
//     * 	Status getStatusByNum(long num);
//     *
//     */
//    public StatusResponseDto getStatusById(long id){
//        Status status = statusRespository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 이력이 없습니다. statusId =" + id));
//
//        return new StatusResponseDto(status);
//    }
//
//    /**
//     *  4. latitude, longitude로 건물 정보 찾기 -> 군집 해당하는 목록 모두 (1개씩)
//     *  List<Building> getBuildingDetail(Building building);
//     *
//     */
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
//
//    /**
//     * 5. 해당 주소의 상태 이력 모두 조회 -> 원장으로 보내기 위해
//     * List<Status> findByAddress(Status status);
//     *
//     */
//    public List<Status> findAllByAddressAndFloorAndHo(String address, String floor, String ho){
//        return statusRespository.findAllByAddressAndFloorAndHo(address, floor, ho);
//    }
//
//    /**
//     *  6. status 테이블에서 ID로 이미지 가져오기
//     *  String getStatusImage(long statusId);
//     *
//     */
//    public String getStatusImage(ContractFindResponseDto contractFindResponseDto){
//        return statusRespository.getStatusImage(contractFindResponseDto.getAddress(), contractFindResponseDto.getFloor(), contractFindResponseDto.getHo());
//    }
//
//    /**
//     *  7. 공인중개사가 등록한 상태 이력 모두 출력
//     *  List<Building> findAllByLicense(String license);
//     *
//     */
//    public List<StatusResponseDto> findAllByLicense(String license){
//        List<Status> data = statusRespository.findAllByLicense(license);
//        List<StatusResponseDto> result = new ArrayList<>();
//
//        for (Status status : data) {
//            result.add(new StatusResponseDto(status));
//        }
//        return result;
//    }
//}
