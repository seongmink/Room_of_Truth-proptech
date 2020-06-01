package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.ContractResponseDto;
import com.roomoftruth.rot.dto.ContractSaveRequestDto;
import com.roomoftruth.rot.repository.ContractRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractService {

    private final ContractRespository contractRepository;

    /**
     *  1. 계약 이력 등록하기
     * 	saveContract(ContractSaveRequestDto contractSaveRequestDto)
     *
     */
    @Transactional
    public long saveContract(ContractSaveRequestDto contractSaveRequestDto){
        Contract response = contractSaveRequestDto.toEntity();

        //
        // fabric 처리후
        System.out.println("===== FABRIC에서 등록 해야 함 =====");
        //

        contractRepository.save(response);

        return response.getContractId();
    }

    /**
     *  2. 모든 빌딩 찾아오기
     * 	public List<Contract> findAll()
     *
     */
    public List<Contract> findAll(){
        return contractRepository.findAll();
    }

    /**
     *  3. ID로 이력 검색
     * 	Building getBuildingByNum(long num);
     *
     */
    public ContractResponseDto getContractById(long id){
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 이력이 없습니다. contractId =" + id));

        return new ContractResponseDto(contract);
    }

    /**
     *  4. latitude, longitude로 건물 정보 찾기 -> 군집 해당하는 목록 모두
     *  List<Building> getBuildingDetail(Building building);
     *
     */
    public List<ContractResponseDto> findDistinctByLatitudeAndLongitude(String latitude, String longitude){
        List<Contract> data = contractRepository.findDistinctByLatitudeAndLongitude(latitude, longitude);
        List<ContractResponseDto> result = new ArrayList<>();

        for (Contract contract: data) {
            result.add(new ContractResponseDto(contract));
        }

        return result;
    }

    /**
     * 5. 해당 주소의 계약이력 모두 조회 -> 원장으로 보내기 위해
     * List<Building> getBuildingNum(Building building);
     *
     */
    public List<ContractResponseDto> findAllByAddressAndFloorAndHo(String address, String floor, String ho){
        List<Contract> data = contractRepository.findAllByAddressAndFloorAndHo(address, floor, ho);
        List<ContractResponseDto> result = new ArrayList<>();

        for (Contract contract : data) {
            result.add(new ContractResponseDto(contract));
        }

        return result;
    }

    /**
     *  6. contract 테이블에서 ID로 이미지 가져오기
     *  String getContractImage(long contractId);
     *
     */
    public String findImageById(long contractId){
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 이력이 존재하지 않습니다. id = " + contractId))
                .getImage();
    }

    /**
     *  7. 공인중개사가 등록한 계약 이력 모두 출력
     *  List<Building> findAllByLicense(String license);
     *
     */
    public List<ContractResponseDto> findAllByLicense(String license){
        List<Contract> data = contractRepository.findAllByLicense(license);
        List<ContractResponseDto> result = new ArrayList<>();

        for (Contract contract : data) {
            result.add(new ContractResponseDto(contract));
        }
        return result;
    }

}
/**
 *  해당 주소(도로명, floor, ho) 해당하는 모든 계약, 유지보수 찾기
 * 	List<Building> getDetailList(Building tempBuilding);
 *
 *
 *
 *  latitude, longitude로 유지보수 정보 찾기 -> 군집 해당하는 목록 모두
 *  List<Building> getMaintenanceDetail(Building b);
 *
 *  status 테이블에서 ID로 이미지 가져오기
 * 	String getStatusImage(long status_id);
 *
 *  공인중개사가 등록한 status 이력 가져오기
 * 	List<Maintenance> getAgentContributionM(String license);
 */