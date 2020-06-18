package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.record.ContractDetailRequestDto;
import com.roomoftruth.rot.dto.record.ContractSaveRequestDto;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.service.*;
import com.roomoftruth.rot.util.AddressChangeUtil;
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

    static long contract_idx = 1;

    /**
     * @param contractSaveRequestDto
     * @return contractID
     * @throws IOException
     */
    @PostMapping("/contract/save")
    @ApiOperation("계약 이력 등록하기")
    public String save(@RequestBody ContractSaveRequestDto contractSaveRequestDto) throws Exception {
        System.out.println("Request :: " + contractSaveRequestDto.toString());

        // Address Util Change
        String[] addr = contractSaveRequestDto.getAddress().split(" ");
        AddressChangeUtil addressChangeUtil = new AddressChangeUtil();
        addr[0] = addressChangeUtil.addressChange(addr[0]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addr.length; i++) {
            sb.append(addr[i] + " ");
        }

        contractSaveRequestDto.setAddress(sb.toString().trim());

        // 전세나 매매의 경우 월세가 없다
        if (contractSaveRequestDto.getMonthly() == "" ||
                contractSaveRequestDto.getMonthly().equals("")) {
            contractSaveRequestDto.setMonthly("0");
        }

        // 주소로 address_id 값 찾아오기
        long aroundId = 0;

        if (aroundService.findTop1ByAddress(contractSaveRequestDto.getAddress()) == null) {
            System.out.println("Around 정보가 없는 데이터 입니다.");
            aroundId = -1;
            return String.valueOf(aroundId);
        } else {
            aroundId = aroundService.findTop1ByAddress(contractSaveRequestDto.getAddress()).getAroundId();
        }

        Contract contract = new Contract(contract_idx, aroundId, contractSaveRequestDto);

        // DB에 이력 저장 시작
        if (contractService.saveContract(contract) == contract_idx) {
            System.out.println("DB 저장 성공");
            contract_idx++;
            agentService.pointUp(contract.getLicense());
            System.out.println("point Up 성공");
            return String.valueOf(contract_idx - 1);
        } else {
            return "DB 저장 실패";
        }
    }

    /**
     * @param city
     * @param local
     * @return List<Contracts, Statuses> findAll by city
     */
    @GetMapping("/contract/search")
    @ApiOperation("조회하기에 모든 이력 뿌려주기")
    public List<Object> getAllContracts(@RequestParam String city, @RequestParam String local) {
        String key = city + " " + local;

        List<Object> result = new ArrayList<>();
        List<Contract> contracts = contractService.findAllContractByCity(key);
        List<Status> statuses = statusService.findAllStatusByCity(key);

        while(contracts.size() > 0 && statuses.size() > 0){
            if(contracts.get(0).getContractDate().compareTo(statuses.get(0).getStartDate()) > 0){
                result.add(contracts.remove(0));
            } else {
                result.add(statuses.remove(0));
            }
        }

        if(contracts.size() > 0){
            result.addAll(contracts);
        } else if(statuses.size() > 0)
            result.addAll(statuses);

        return result;
    }

    /**
     * @param requestDto (address, floor, ho)
     * @return List<Contracts, Statuses> by building
     */
//    @PostMapping("/contract/lists")
//    @ApiOperation("군집에 해당하는 이력 LIST 조회하기")
//    public List<Contract> getAllDetails(@RequestBody ContractFindRequestDto[] requestDto) {
//        System.out.println("====== POST : api/details");
//        String sd = requestDto[0].getSd();
//        String sgg = requestDto[0].getSgg();
//        String key = sd + " " + sgg;
//
//        List<Contract> searchData = contractService.findAllByAddressContaining(key);
//
//        List<Contract> result = new ArrayList<>();
//
//        result = contractService.getAllDetails(requestDto, searchData);
//
//        return result;
//    }

    /**
     * @param num
     * @return agent_license
     */
    @GetMapping("/agent/{num}")
    @ApiOperation("이력 작성시 공인중개사 번호 가져오기")
    public String getAgentLicense(@PathVariable Long num) {
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

        List<Contract> contracts = new ArrayList<>();
        List<Status> statuses = new ArrayList<>();

        long aroundId = contractDetailRequestDto.getAroundId();
        String floor = contractDetailRequestDto.getFloor();
        String ho = contractDetailRequestDto.getHo();

        contracts = contractService.findAllByAddressAndFloorAndHo(aroundId, floor, ho);
        statuses = statusService.findAllByAroundIdAndFloorAndHo(aroundId, floor, ho);

        List<Object> result = new ArrayList<>();

        while(contracts.size() > 0 && statuses.size() > 0){
            if(contracts.get(0).getContractDate().compareTo(statuses.get(0).getStartDate()) > 0){
                result.add(contracts.remove(0));
            } else {
                result.add(statuses.remove(0));
            }
        }

        if(contracts.size() > 0){
            result.addAll(contracts);
        } else if(statuses.size() > 0)
            result.addAll(statuses);

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
            System.out.println("type 0 :: Contract 상세 조회 ");
            return contractService.findById(num);
        } else {
            System.out.println("type 1 :: Status 상세 조회 ");
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