package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.contracts.*;
import com.roomoftruth.rot.fabric.ContractRecord;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final IFabricCCService iFabricCCService;
    private final ContractSearchRepository contractSearchRepository;
    private final ContractListRepository contractListRepository;
    private final ContractImageRepository contractImageRepository;
    private final ContractDetailRepository contractDetailRepository;
    private final ContractConfirmRepository contractConfirmRepository;

    /**
     * 1. 계약 이력 등록하기
     * saveContract(ContractSaveRequestDto contractSaveRequestDto)
     */
    @Transactional
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    /**
     * 2. ID로 이력 찾기
     * @param num
     * @return
     */
    public ContractConfirmResponseDto findConfirmById(long num) {
        ContractConfirmResponseDto contract = contractConfirmRepository.findConfirmById(num);

        return contract;
    }

    /**
     * 3. 도시에 모든 건물당 이력 찾기
     * @param key
     * @return
     */
    public List<ContractListResponseDto> findAllContractsList(String key){
        return contractListRepository.findAllContractsList(key);
    }

    /**
     * 4. 도시에 모든 사진 찾아오기
     * @param key
     * @return
     */
    public List<ContractImageRequestDto> findContractImages(String key){
        return contractImageRepository.findAllContractImages(key);
    }

    /**
     * 5. 상세이력 찾기
     * @param aroundId
     * @param floor
     * @param ho
     * @return
     */
    public List<ContractDetailResponseDto> findAllContractsByAroundAndFloorAndHo(long aroundId, String floor, String ho){
        return contractDetailRepository.findAllContractsByAroundAndFloorAndHo(aroundId, floor, ho);
    }

    /**
     * 6. 도시의 모든 Contract 주소와 위도, 경도
     * @return all address, latitude, longitude
     */
    public List<ContractSearchResponseDto> findAllContractByCity(String key) {
        return contractSearchRepository.findAllContractByCity(key);
    }

    /**
     * 7. DB에 있는 이력 Fabric Network로 전송
     * @param start
     * @param end
     */
    public void dataTransfer(int start, int end){
        List<Contract> data = contractRepository.dataTransfer(start, end);
        System.out.println(data.size());
        for(int i = 0; i < data.size(); i++){
            Contract temp = data.get(i);

            ContractRecord contractRecord = new ContractRecord(temp);
            iFabricCCService.registerContract(contractRecord);
        }
    }

    /**
     * 8. userId로 Agent정보 찾기
     * @param user_id
     * @return Agent.License
     */
    public String getAgentLicense(Long user_id) {
        return contractRepository.getAgentLicense(user_id);
    }

}