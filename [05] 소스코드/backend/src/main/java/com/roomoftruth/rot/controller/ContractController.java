package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.contracts.*;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;
    private final AroundService aroundService;
    private final IFabricCCService iFabricCCService;
    private final AgentService agentService;
    private final StatusService statusService;

    /**
     * @param contractSaveRequestDto
     * @return contractID
     * @throws IOException
     */
    @PostMapping("/contract/save")
    @ApiOperation("계약 이력 등록하기")
    public String save(@RequestBody ContractSaveRequestDto contractSaveRequestDto) throws Exception {
        System.out.println("====== POST : api/contract/save");

        // 전세나 매매의 경우 월세가 없다
        if (contractSaveRequestDto.getMonthly() == "" ||
                contractSaveRequestDto.getMonthly().equals("")) {
            contractSaveRequestDto.setMonthly("0");
        }

        long aroundId = aroundService.findTop1ByAddress(contractSaveRequestDto.getAddress()).getAroundId();

        Contract contract = new Contract(aroundId, contractSaveRequestDto);
        System.out.println("Before : " + contract);
        Contract result = contractService.saveContract(contract);
        agentService.pointUp(contract.getLicense());
        return String.valueOf(result.getContractId());
    }

    /**
     * @param city
     * @param local
     * @return List<Contracts, Statuses> findAll by city
     */
    @GetMapping("/contract/search")
    @ApiOperation("조회하기에 모든 이력 뿌려주기")
    public List<Object> getAllContracts(@RequestParam String city, @RequestParam String local) {
        System.out.println("====== GET : api/contract/search");
        String key = city + " " + local;

        List<Object> result = new ArrayList<>();
        List<ContractSearchResponseDto> contracts = contractService.findAllContractByCity(key);
        List<ContractSearchResponseDto> statuses = statusService.findAllStatusByCity(key);

        result.addAll(contracts);
        result.addAll(statuses);

        return result;
    }

    /**
     * @param arounds
     * @return findAllLists
     */
    @PostMapping("/contract/lists")
    @ApiOperation("군집에 해당하는 이력 LIST 조회하기")
    public List<ContractListImageResponseDto> getAllDetails(@RequestBody ContractListRequestDto[] arounds) {
        System.out.println("====== POST : api/contract/lists");

        // 해당 시도, 시군구의 모든 이력 저장
        String key = arounds[0].getSd() + " " + arounds[0].getSgg();
        List<ContractListResponseDto> contracts = contractService.findAllContractsList(key);

        List<ContractListImageResponseDto> result = new ArrayList<>();

        System.out.println("contracts Size : " + contracts.size());

        for (ContractListRequestDto request : arounds) {
            for (ContractListResponseDto dto : contracts) {
                System.out.println("longitude : " + dto.getLongitude() + ", latitude : " + dto.getLatitude());
                System.out.println("Request : " + request.getLongitude() + ", " + request.getLatitude());
                if (dto.getLongitude().contains(request.getLongitude()) &&
                        dto.getLatitude().contains(request.getLatitude())) {
                    result.add(new ContractListImageResponseDto(dto));
                }
            }
        }

        System.out.println("result Size :  "+ result.size());

        // 모든 이미지들 imageMap에 저장
        List<ContractImageRequestDto> contractImages = contractService.findContractImages(key);
        List<ContractImageRequestDto> statusImages = statusService.findStatusImages(key);

        contractImages.addAll(statusImages);

        for (ContractListImageResponseDto dto : result) {
            for (ContractImageRequestDto imageDto : contractImages) {
                if (Integer.parseInt(imageDto.getContractId() + "") == Integer.parseInt(dto.getContractId() + "")) {
                    dto.setImage(imageDto.getImage());
                    break;
                }
            }
        }

        return result;
    }

    /**
     * @param num
     * @return agent_license
     */
    @GetMapping("/agent/{num}")
    @ApiOperation("이력 작성시 공인중개사 번호 가져오기")
    public String getAgentLicense(@PathVariable Long num) {
        System.out.println("====== GET : api/agent/{num}");
        return contractService.getAgentLicense(num);
    }

    /**
     * @param contractDetailRequestDto
     * @return List<All>
     * @throws IOException
     */
    @PostMapping("/contract/detail")
    @ApiOperation("계약 이력 상세 정보 확인")
    public List<Object> getBuildingDetail(@RequestBody ContractDetailRequestDto contractDetailRequestDto) throws IOException {
        System.out.println("POST : /api/contract/detail");

        List<ContractDetailResponseDto> contracts = new ArrayList<>();
        List<StatusDetailResponseDto> statuses = new ArrayList<>();

        long aroundId = contractDetailRequestDto.getAroundId();
        String floor = contractDetailRequestDto.getFloor();
        String ho = contractDetailRequestDto.getHo();

        contracts.addAll(contractService.findAllContractsByAroundAndFloorAndHo(aroundId, floor, ho));
        statuses.addAll(statusService.findAllStatusByAroundAndFloorAndHo(aroundId, floor, ho));

        List<Object> result = new ArrayList<>();

        while (contracts.size() > 0 && statuses.size() > 0) {
            if (contracts.get(0).getContractDate().compareTo(statuses.get(0).getStartDate()) > 0) {
                result.add(contracts.remove(0));
            } else {
                result.add(statuses.remove(0));
            }
        }

        if (contracts.size() > 0){
            while(contracts.size() > 0){
                result.add(contracts.remove(contracts.size() - 1));
            }
        }

        if (statuses.size() > 0) {
            while (statuses.size() > 0) {
                result.add(statuses.remove(statuses.size() - 1));
            }
        }

        return result;
    }

    /**
     * @param startIndex
     * @param endIndex
     * @return DB -> BlockChain Data Transfer
     */
    @GetMapping("/dataTransfer")
    @ApiOperation("원장에 데이터 등록하기 startIndex ~ endIndex")
    public void dataTransfer(@RequestParam int startIndex, int endIndex) {
        contractService.dataTransfer(startIndex, endIndex);
    }

    @PostMapping("/contract/confirm")
    @ApiOperation("계약 이력 상세 정보 확인")
    public Object getBuildingDetail(@RequestParam("type") int type, @RequestParam("num") long num) throws IOException {
        System.out.println("POST : /api/contract/confirm ");
        if (type == 0) {
            return contractService.findById(num);
        } else {
            return statusService.findById(num);
        }
    }

    /**
     * @return
     * @throws IOException
     */
    @GetMapping("/loadchannel")
    @ApiOperation("채널 한번 로드하기")
    public boolean loadChannel() throws IOException {
        return iFabricCCService.loadChannel();
    }
}