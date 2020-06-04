package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.repository.ContractFindLocationDtoRepository;
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
    private final ContractFindLocationDtoRepository contractFindLocationDtoRepository;
    private final StatusService statusService;

    /**
     *  1. 계약 이력 등록하기
     * 	saveContract(ContractSaveRequestDto contractSaveRequestDto)
     *
     */
    @Transactional
    public long saveContract(ContractSaveRequestDto contractSaveRequestDto){
        System.out.println("서비스 왔다");

        // fabric 처리후
        System.out.println("===== FABRIC에서 등록 해야 함 =====");
        //

        Contract contract = new Contract(contractSaveRequestDto);
        contractRepository.save(contract);
        System.out.println("DB 등록 성공");
        return contract.getContractId();
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
    public String getContractImage(ContractFindResponseDto contractFindResponseDto){
        return contractRepository.getContractImage(contractFindResponseDto.getAddress(), contractFindResponseDto.getFloor(), contractFindResponseDto.getHo());
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
     *  8. 해당 주소(도로명, floor, ho, image) 해당하는 모든 계약, 유지보수 찾기
     *  List<ContractResponseDto> findAllDetails(String latitude, String longitude);
     *
     */
    public List<ContractResultDto> findAllDetails(ContractFindRequestDto[] request) {

        List<ContractResultDto> result = new ArrayList<>();
        HashSet<ContractFindRequestDto> set = new HashSet<>();

        for (int i = 0; i < request.length; i++) {
            set.add(request[i]);
        }

        Iterator<ContractFindRequestDto> it = set.iterator();

        while (it.hasNext()) {
            ContractFindRequestDto contractTemp = it.next();
            System.out.println("findAllDetails 시작");
            System.out.println("latitude : " + contractTemp.getLatitude());
            System.out.println("longitude : " + contractTemp.getLongitude());
            List<ContractFindResponseDto> save = contractFindResponseRepository.findAllDetails(contractTemp.getLatitude(), contractTemp.getLongitude());

            System.out.println("=========== " + save.size() + " =======");
            for (int i = 0; i < save.size(); i++) {
                System.out.println("for start");
                System.out.println(save.get(i));

                String image = getContractImage(save.get(i));

                if (image == null) {
                    image = statusService.getStatusImage(save.get(i));
                }

                if(image == null)
                    image = "default.png";

                //address, floor, ho,latitude, longitude 있는 상태 -> save.get(i)
                ContractResultDto contractResultDto = ContractResultDto.builder()
                        .address(save.get(i).getAddress())
                        .floor(save.get(i).getFloor())
                        .ho(save.get(i).getHo())
                        .image(image)
                        .build();

                result.add(contractResultDto);
            }
        }
        return result;
    }

    /**
     * 모든 Contract주소와 위도, 경도
     * @return all address, latitude, longitude
     */

//    2번 방식. 이게 더 빠름
    public ContractFindLocationDto findContractLocation(String address){
        return contractFindLocationDtoRepository.findContractLocation(address);
    }

//    느린 1번 방식
//    public Contract findContractLocation(String address) {
//        return contractRepository.findContractLocation(address);
//    }
}