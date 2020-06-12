package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.fabric.ContractRecord;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.repository.ContractRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
//    private final ContractDetailsResponseDtoRepository contractDetailsResponseDtoRepository;
//    private final ContractFindResponseRepository contractFindResponseRepository;
//    private final ContractFindLocationDtoRepository contractFindLocationDtoRepository;
//    private final StatusService statusService;
    private final IFabricCCService iFabricCCService;

    /**
     * 1. 계약 이력 등록하기
     * saveContract(ContractSaveRequestDto contractSaveRequestDto)
     */
    @Transactional
    public long saveContract(ContractRecord contractRecord) {
        Contract contract = new Contract(contractRecord);
        return contractRepository.save(contract).getContractId();
    }

    /**
     * 2. 모든 빌딩 찾아오기
     * public List<Contract> findAll()
     */
//    public List<Contract> findAll() {
//        return contractRepository.findAll();
//    }

    /**
     * 3. ID로 이력 검색
     * Building getBuildingByNum(long num);
     */
//    public ContractResponseDto getContractById(long id) {
//        Contract contract = contractRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 이력이 없습니다. contractId =" + id));
//
//        return new ContractResponseDto(contract);
//    }

    /**
     * 4. latitude, longitude로 건물 정보 찾기 -> 군집 해당하는 목록 모두 (1개씩)
     * List<Building> getBuildingDetail(Building building);
     */
//    public List<ContractResponseDto> findDistinctByLatitudeAndLongitude(String latitude, String longitude) {
//        List<Contract> data = contractRepository.findDistinctByLatitudeAndLongitude(latitude, longitude);
//        List<ContractResponseDto> result = new ArrayList<>();
//
//        for (Contract contract : data) {
//            result.add(new ContractResponseDto(contract));
//        }
//
//        return result;
//    }

    /**
     * 5. 해당 주소의 계약이력 모두 조회 -> 원장으로 보내기 위해
     * List<Building> getBuildingNum(Building building);
     */
//    public List<Contract> findAllByAddressAndFloorAndHo(String address, String floor, String ho) {
//        return contractRepository.findAllByAddressAndFloorAndHo(address, floor, ho);
//    }

    /**
     * 6. contract 테이블에서 주소(address, floor, ho)로 이미지 1개 가져오기
     * String getContractImage(ContractRequestDto);
     */
//    public String getContractImage(ContractFindResponseDto contractFindResponseDto) {
//        return contractRepository.getContractImage(contractFindResponseDto.getAddress(), contractFindResponseDto.getFloor(), contractFindResponseDto.getHo());
//    }

    /**
     * 7. 공인중개사가 등록한 계약 이력 모두 출력
     * List<Building> findAllByLicense(String license);
     */
//    public List<ContractResponseDto> findAllByLicense(String license) {
//        List<Contract> data = contractRepository.findAllByLicense(license);
//        List<ContractResponseDto> result = new ArrayList<>();
//
//        for (Contract contract : data) {
//            result.add(new ContractResponseDto(contract));
//        }
//        return result;
//    }

    /**
     * 모든 Contract주소와 위도, 경도
     *
     * @return all address, latitude, longitude
     */
//    public List<ContractFindLocationDto> findContractLocations(String key) {
//        return contractFindLocationDtoRepository.findContractLocations(key);
//    }

    /**
     * 시도,시군구 포함된 이력 리턴
     */
//    public List<Contract> findAllByAddressContaining(String key) {
//        return contractRepository.findAllByAddressContaining(key);
//    }

    /**
     * 모든 데이터에서 위도, 경도로 찾아주기
     */
//    public List<Contract> getAllDetails(ContractFindRequestDto[] requestDto,
//                                        List<Contract> contracts) {
//
//        List<Contract> result = new ArrayList<>();
//
//        Set<Location> set = new HashSet<>();
//        for (int i = 0; i < contracts.size(); i++) {
//            set.add(new Location(contracts.get(i).getLatitude(), contracts.get(i).getLongitude(),
//                    contracts.get(i).getFloor(), contracts.get(i).getHo()));
//        }
//
//        List<Location> temp = new ArrayList<>();
//        for (int j = 0; j < requestDto.length; j++) {
//            String latitude = requestDto[j].getLatitude().substring(0, requestDto[j].getLatitude().length() - 3);
//            String longitude = requestDto[j].getLongitude().substring(0, requestDto[j].getLongitude().length() - 3);
//            Iterator<Location> iterator = set.iterator();
//            while (iterator.hasNext()) {
//                Location it = iterator.next();
//                if (it.getLatitude().contains(latitude) && it.getLongitude().contains(longitude)) {
//                    temp.add(it);
//                }
//            }
//        }
//
//        for (int i = 0; i < temp.size(); i++) {
//            for (Contract contract : contracts) {
//                if (contract.getLatitude().equals(temp.get(i).latitude) && contract.getLongitude().equals(temp.get(i).longitude)
//                && contract.getFloor().equals(temp.get(i).floor) && contract.getHo().equals(temp.get(i).ho)) {
//                    result.add(contract);
//                    break;
//                }
//            }
//        }
//        return result;
//    }

//    public void dataTransfer(int start, int end){
//        List<Contract> data = contractRepository.dataTransfer(start, end);
//        System.out.println(data.size());
//        for(int i = 0; i < data.size(); i++){
//            Contract temp = data.get(i);
//            if(temp.getSgg()==null || temp.getSgg() == ""){
//                temp.setSgg("-");
//            }
//            FabricContractRecord record = new FabricContractRecord(temp);
//            iFabricCCService.registerContract(record);
//        }
//    }

//    /**
//     * @param user_id
//     * @return Agent.License
//     */
//    public String getAgentLicense(Long user_id) {
//        return contractRepository.getAgentLicense(user_id);
//    }

//    @Data
//    public class Location {
//        private String latitude;
//        private String longitude;
//        private String floor;
//        private String ho;
//
//        public Location(String la, String lo, String fl, String ho) {
//            this.latitude = la;
//            this.longitude = lo;
//            this.floor = fl;
//            this.ho = ho;
//        }
//    }
}