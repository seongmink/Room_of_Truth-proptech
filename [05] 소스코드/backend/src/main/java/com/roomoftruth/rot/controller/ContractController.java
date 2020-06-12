package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.dto.fabric.ContractSaveRequestDto;
import com.roomoftruth.rot.fabric.ContractRecord;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.service.*;
import com.roomoftruth.rot.util.AddressChangeUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;
//    private final StatusService statusService;
    private final AroundService aroundService;
    private final IFabricCCService iFabricCCService;
    private final AgentService agentService;
    private final FavoriteService favoriteService;
    private final AddressService addressService;

    static long contract_idx = 200200207;

    /**
     *
     * @param contractSaveRequestDto
     * @return contractID
     * @throws IOException
     */
    @PostMapping("/contract/save")
    @ApiOperation("계약 이력 등록하기")
    public String save(@RequestBody ContractSaveRequestDto contractSaveRequestDto) throws Exception {
        System.out.println("Request :: " + contractSaveRequestDto.toString());
        // 시도 Address Util Change
        String[] addr = contractSaveRequestDto.getAddress().split(" ");
        AddressChangeUtil addressChangeUtil = new AddressChangeUtil();
        addr[0] = addressChangeUtil.addressChange(addr[0]);

        StringBuilder sb = new StringBuilder();
        for(int i= 0; i < addr.length; i++){
            sb.append(addr[i]+" ");
        }

        contractSaveRequestDto.setAddress(sb.toString().trim());

        // 전세나 매매의 경우 월세가 없다
        if (contractSaveRequestDto.getMonthly() == "" ||
                contractSaveRequestDto.getMonthly().equals("")) {
            contractSaveRequestDto.setMonthly("0");
        }

        String PK = "TEST_C" + contract_idx;

        // 주소로 address_id 값 찾아오기
        long aroundId = 0;

        if(aroundService.findTop1ByAddress(contractSaveRequestDto.getAddress()) == null){
            // 1. AddressID -1 설정 후 반환
            System.out.println("Around 정보가 없는 데이터 입니다.");
            aroundId = -1;
            return String.valueOf(aroundId);
        } else {
            aroundId = aroundService.findTop1ByAddress(contractSaveRequestDto.getAddress()).getAroundId();
        }

        // 블록체인에 넣을 ContractRecord 만들어서 요청 날리자
        ContractRecord contractRecord = new ContractRecord(PK, aroundId, contractSaveRequestDto);

        System.out.println("-- 넣기 직전 : " + contractRecord);
        boolean result = iFabricCCService.registerContract(contractRecord);
        if (result == true) {
            System.out.println("원장 저장 성공");
            contractRecord.setContract_id(String.valueOf(contract_idx));

            if(contractService.saveContract(contractRecord) == contract_idx){
                System.out.println("DB 저장 성공");
                contract_idx++;
                agentService.pointUp(contractRecord.getLicense());
                System.out.println("point Up 성공");
                return String.valueOf(contract_idx - 1);
            } else {
                return "DB 저장 실패";
            }
        } else {
            return "실패";
        }
    }

    /**
     *
     * @param city
     * @param local
     * @return List<Contracts, Statuses> findAll by city
     */
//    @GetMapping("/contract/search")
//    @ApiOperation("조회하기에 모든 이력 뿌려주기")
//    public List<ContractFindLocationDto> getAllContracts(@RequestParam String city, @RequestParam String local) {
//        String key = city + " " + local;
//        List<Around> allAddress = aroundService.findAllAddress(key);
//
//        List<ContractFindLocationDto> result = contractService.findContractLocations(key);
//        return result;
//    }

    /**
     *
     * @param requestDto (address, floor, ho)
     * @return List<Contracts,Statuses> by building
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
     *
     * @param num
     * @return agent_license
     */
//    @GetMapping("/agent/{num}")
//    @ApiOperation("이력 작성시 공인중개사 번호 가져오기")
//    public String getAgentLicense(@PathVariable Long num){
//        return contractService.getAgentLicense(num);
//    }

    /**
     *
     * @param request (address, floor, ho)
     * @return List<FabricConstract, FabricStatus>
     * @throws IOException
     */
//    @PostMapping("/contract/detail")
//    @ApiOperation("계약 이력 상세 정보 확인")
//    public List<FabricResponseDto> getBuildingDetail(@RequestBody FabricResponseDto request) throws IOException {
//        System.out.println("POST : /api/contract/detail");
//
//        List<Contract> contracts = new ArrayList<Contract>();
//        List<Status> statuses = new ArrayList<Status>();
//
//        String address = request.getAddress();
//        String floor = request.getFloor();
//        String ho = request.getHo();
//
//        contracts = contractService.findAllByAddressAndFloorAndHo(address, floor, ho);
//        statuses = statusService.findAllByAddressAndFloorAndHo(address, floor, ho);
//
//        List<FabricResponseDto> result = new ArrayList<>();
//
//        for (int i = 0; i < contracts.size(); i++) {
//            FabricContractRecord fabricContractRecord = new FabricContractRecord();
//            FabricResponseDto contractOne = new FabricResponseDto();
//
//            fabricContractRecord = iFabricCCService.queryContract("CONTRACT" + contracts.get(i).getContractId());
//
//            System.out.println(fabricContractRecord.toString());
//
//            contractOne.setContractId(fabricContractRecord.getContract_id());
//            contractOne.setAddress(fabricContractRecord.getAddress());
//            contractOne.setLongitude(fabricContractRecord.getLongitude());
//            contractOne.setLatitude(fabricContractRecord.getLatitude());
//            contractOne.setExclusive(fabricContractRecord.getExclusive());
//            contractOne.setFloor(fabricContractRecord.getFloor());
//            contractOne.setHo(fabricContractRecord.getHo());
//            contractOne.setKind(fabricContractRecord.getKind());
//            contractOne.setDetail(fabricContractRecord.getDetail());
//            contractOne.setCost(fabricContractRecord.getCost());
//            contractOne.setMonthly(fabricContractRecord.getMonthly());
//            contractOne.setLicense(fabricContractRecord.getLicense());
//            contractOne.setImage(fabricContractRecord.getImage());
//            contractOne.setContractDate(fabricContractRecord.getContract_date());
//
//            result.add(contractOne);
//        }
//
//        for (int i = 0; i < statuses.size(); i++) {
//            FabricStatusRecord fabricStatusRecord = new FabricStatusRecord();
//            FabricResponseDto statusOne = new FabricResponseDto();
//
//            fabricStatusRecord = iFabricCCService.queryStatus("STATUS" + statuses.get(i).getStatusId());
//
//            System.out.println(fabricStatusRecord.toString());
//
//            statusOne.setContractId(fabricStatusRecord.getStatus_id());
//            statusOne.setAddress(fabricStatusRecord.getAddress());
//            statusOne.setLongitude(fabricStatusRecord.getLongitude());
//            statusOne.setLatitude(fabricStatusRecord.getLatitude());
//            statusOne.setFloor(fabricStatusRecord.getFloor());
//            statusOne.setHo(fabricStatusRecord.getHo());
//            statusOne.setKind(fabricStatusRecord.getCategory());
//            statusOne.setDetail(fabricStatusRecord.getDetail());
//            statusOne.setCost(fabricStatusRecord.getCost());
//            statusOne.setLicense(fabricStatusRecord.getLicense());
//            statusOne.setImage(fabricStatusRecord.getImage());
//            statusOne.setExclusive(fabricStatusRecord.getExclusive());
//            statusOne.setContractDate(fabricStatusRecord.getStart_date());
//            statusOne.setEndDate(fabricStatusRecord.getEnd_date());
//
//            result.add(statusOne);
//        }
//
//
//        for (int i = 0; i < result.size(); i++){
//            long temp = 0;
//            String addr = result.get(i).getAddress();
//            System.out.println("========== 시작 =============");
//            Long aroundId = aroundService.findByAddress(addr);
//            System.out.println("ID : " + aroundId);
//            System.out.println("around clear !!!");
//
//            long score = 0;
//            if(favoriteService.findByAroundIdInFavorite(aroundId) != null){
//                score = favoriteService.findByAroundId(aroundId);
//                System.out.println(" ------- favorie clear !!! --------");
//            }
//
//            if(score > 0)
//                temp = score;
//            result.get(i).setIsLike(String.valueOf(temp));
//        }
//
//        System.out.println("--- sort 시작 ----");
//        Collections.sort(result, new Comparator<FabricResponseDto>() {
//
//            @Override
//            public int compare(FabricResponseDto o1, FabricResponseDto o2) {
//                return o2.getContractDate().compareTo(o1.getContractDate());
//            }
//        });
//
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i).toString());
//        }
//        System.out.println("result Size :  " + result.size());
//        return result;
//    }

    /**
     *
     * @param startIndex
     * @param endIndex
     * @return DB -> BlockChain Data Transfer
     */
//    @GetMapping("/dataTransfer")
//    @ApiOperation("원장에 데이터 등록하기 startIndex ~ endIndex")
//    public void dataTransfer(@RequestParam int startIndex, int endIndex) {
//        contractService.dataTransfer(startIndex, endIndex);
//    }


//    @PostMapping("/contract/confirm")
//    @ApiOperation("계약 이력 상세 정보 확인")
//    public Object getBuildingDetail(@RequestParam("type") int type, @RequestParam("num") long num) throws IOException {
//        System.out.println("POST : /api/contract/confirm ");
//        if (type == 0) {
//            FabricContractRecord fabricContractRecord = new FabricContractRecord();
//            fabricContractRecord = iFabricCCService.queryContract("CONTRACT" + num);
//            System.out.println(fabricContractRecord.toString());
//            return fabricContractRecord;
//        } else {
//            FabricStatusRecord fabricStatusRecord = new FabricStatusRecord();
//            fabricStatusRecord = iFabricCCService.queryStatus("STATUS" + num);
//            StatusConfirmDto statusConfirmDto = new StatusConfirmDto(fabricStatusRecord);
//            return statusConfirmDto;
//        }
//    }

    /**
     *
     * @return loadChannel
     * @throws IOException
     */
    @GetMapping("/loadchannel")
    @ApiOperation("채널 한번 로드하기")
    public boolean loadChannel() throws IOException {
        return iFabricCCService.loadChannel();
    }
}