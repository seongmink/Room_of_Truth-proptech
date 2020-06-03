package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.repository.ContractFindResponseRepository;
import com.roomoftruth.rot.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractFindResponseRepository contractFindResponseRepository;
    private final StatusService statusService;

    /**
     *  1. 계약 이력 등록하기
     * 	saveContract(ContractSaveRequestDto contractSaveRequestDto)
     *
     */
    @Transactional
    public long saveContract(ContractSaveRequestDto contractSaveRequestDto){
        Contract response = contractSaveRequestDto.toEntity();

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
     *  4. latitude, longitude로 건물 정보 찾기 -> 군집 해당하는 목록 모두 (1개씩)
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
     *  6. contract 테이블에서 주소(address, floor, ho)로 이미지 1개 가져오기
     *  String getContractImage(ContractRequestDto);
     *
     */
    public String getContractImage(ContractFindRequestDto contractFindRequestDto){
        return contractRepository.getContractImage(contractFindRequestDto.getAddress(), contractFindRequestDto.getFloor(), contractFindRequestDto.getHo());
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

    /**
     *  8. 해당 주소(도로명, floor, ho) 해당하는 모든 계약, 유지보수 찾기
     *  List<ContractResponseDto> findAllDetails(String latitude, String longitude);
     *
     */
    public List<ContractResultDto> findAllDetails(Contract[] request) {

        List<ContractResultDto> result = new ArrayList<>();
        HashSet<Contract> set = new HashSet<>();

        for (int i = 0; i < request.length; i++) {
            set.add(request[i]);
        }

        Iterator<Contract> it = set.iterator();

        while (it.hasNext()) {
            Contract contractTemp = it.next();
            System.out.println("findAllDetails 시작");
            System.out.println("latitude : " + contractTemp.getLatitude());
            System.out.println("longitude : " + contractTemp.getLongitude());
            List<ContractFindResponseDto> save = contractFindResponseRepository.findAllDetails(contractTemp.getLatitude(), contractTemp.getLongitude());

            System.out.println("=========== " + save.size() + " =======");
            for (int i = 0; i < save.size(); i++) {
                System.out.println("for start");
                System.out.println(save.get(i));

                Contract contract = save.get(i).toEntity();
                System.out.println(contract.getAddress());

                ContractFindRequestDto contractFindRequestDto = new ContractFindRequestDto(
                        contract.getAddress(), contract.getFloor(), contract.getHo()
                );

                System.out.println(contractFindRequestDto);
                String image = getContractImage(contractFindRequestDto);
                ContractResultDto contractResultDto = new ContractResultDto(contract.getAddress(), contract.getFloor(),
                        contract.getHo(), image);

                if (image == null) {
                    StatusFindRequestDto statusFindRequestDto = new StatusFindRequestDto(contract.getAddress(),
                            contract.getFloor(), contract.getHo());
                    String statusImage = statusService.getStatusImage(statusFindRequestDto);
                    contractResultDto.setImage("images/"+statusImage);
                } else {
                    contractResultDto.setImage("images/"+image);
                }
                result.add(contractResultDto);
            }
        }
        return result;
    }
}