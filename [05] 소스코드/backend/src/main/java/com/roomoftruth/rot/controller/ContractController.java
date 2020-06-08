package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.fabric.FabricContractRecord;
import com.roomoftruth.rot.fabric.FabricStatusRecord;
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
    private final StatusService statusService;
    private final AroundService aroundService;
    private final IFabricCCService iFabricCCService;
    private final AgentService agentService;
    private final FavoriteService favoriteService;

    static long contract_idx = 162836;

    /**
     *
     * @param contractSaveRequestDto
     * @return contractID
     * @throws IOException
     */
    @PostMapping("/contract/save")
    @ApiOperation("계약 이력 등록하기")
    public String save(@RequestBody @Valid ContractSaveRequestDto contractSaveRequestDto) throws Exception {

        // 시도 Address Util Change
        String[] addr = contractSaveRequestDto.getAddress().split(" ");
        AddressChangeUtil addressChangeUtil = new AddressChangeUtil();
        addr[0] = addressChangeUtil.addressChange(addr[0]);

        StringBuilder sb = new StringBuilder();
        for(int i= 0; i < addr.length; i++){
            sb.append(addr[i]+" ");
        }

        contractSaveRequestDto.setAddress(sb.toString().trim());
        contractSaveRequestDto.setSd(addr[0]);

        contractSaveRequestDto.setSgg("-");
        contractSaveRequestDto.setEmd("-");
        if (contractSaveRequestDto.getMonthly() == "" ||
                contractSaveRequestDto.getMonthly().equals("")) {
            contractSaveRequestDto.setMonthly("0");
        }

        String PK = "CONTRACT" + contract_idx;

        FabricContractRecord fabricContractRecord = new FabricContractRecord(contractSaveRequestDto);
        fabricContractRecord.setContract_id(PK);

        boolean result = iFabricCCService.registerContract(fabricContractRecord);

        if (result == true) {
            fabricContractRecord.setContract_id(String.valueOf(contract_idx));

            if(contractService.saveContract(fabricContractRecord) == contract_idx){
                contract_idx++;
                agentService.pointUp(contractSaveRequestDto.getLicense());

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
    @GetMapping("/contract/search")
    @ApiOperation("조회하기에 모든 이력 뿌려주기")
    public List<ContractFindLocationDto> getAllContracts(@RequestParam String city, @RequestParam String local) {
        String key = city + " " + local;
        List<Around> allAddress = aroundService.findAllAddress(key);

        List<ContractFindLocationDto> result = contractService.findContractLocations(key);

        return result;
    }

    /**
     *
     * @param requestDto (address, floor, ho)
     * @return List<Contracts,Statuses> by building
     */
    @PostMapping("/contract/lists")
    @ApiOperation("군집에 해당하는 이력 LIST 조회하기")
    public List<Contract> getAllDetails(@RequestBody ContractFindRequestDto[] requestDto) {
        System.out.println("====== POST : api/details");
        String sd = requestDto[0].getSd();
        String sgg = requestDto[0].getSgg();
        String key = sd + " " + sgg;

        List<Contract> searchData = contractService.findAllByAddressContaining(key);

        List<Contract> result = new ArrayList<>();

        result = contractService.getAllDetails(requestDto, searchData);

        return result;
    }

    /**
     *
     * @param num
     * @return agent_license
     */
    @GetMapping("/agent/{num}")
    @ApiOperation("이력 작성시 공인중개사 번호 가져오기")
    public String getAgentLicense(@PathVariable Long num){
        return contractService.getAgentLicense(num);
    }

    /**
     *
     * @param request (address, floor, ho)
     * @return List<FabricConstract, FabricStatus>
     * @throws IOException
     */
    @PostMapping("/contract/detail")
    @ApiOperation("계약 이력 상세 정보 확인")
    public List<FabricResponseDto> getBuildingDetail(@RequestBody FabricResponseDto request) throws IOException {
        System.out.println("POST : /api/contract/detail");

        List<Contract> contracts = new ArrayList<Contract>();
        List<Status> statuses = new ArrayList<Status>();

        String address = request.getAddress();
        String floor = request.getFloor();
        String ho = request.getHo();

        contracts = contractService.findAllByAddressAndFloorAndHo(address, floor, ho);
        statuses = statusService.findAllByAddressAndFloorAndHo(address, floor, ho);

        List<FabricResponseDto> result = new ArrayList<>();

        for (int i = 0; i < contracts.size(); i++) {
            FabricContractRecord fabricContractRecord = new FabricContractRecord();
            FabricResponseDto contractOne = new FabricResponseDto();

            fabricContractRecord = iFabricCCService.queryContract("CONTRACT" + contracts.get(i).getContractId());

            System.out.println(fabricContractRecord.toString());

            contractOne.setContractId(fabricContractRecord.getContract_id());
            contractOne.setAddress(fabricContractRecord.getAddress());
//            contractOne.setSd(fabricContractRecord.getSd());
//            contractOne.setSgg(fabricContractRecord.getSgg());
//            contractOne.setEmd(fabricContractRecord.getEmd());
            contractOne.setLongitude(fabricContractRecord.getLongitude());
            contractOne.setLatitude(fabricContractRecord.getLatitude());
            contractOne.setExclusive(fabricContractRecord.getExclusive());
            contractOne.setFloor(fabricContractRecord.getFloor());
            contractOne.setHo(fabricContractRecord.getHo());
            contractOne.setKind(fabricContractRecord.getKind());
            contractOne.setDetail(fabricContractRecord.getDetail());
            contractOne.setCost(fabricContractRecord.getCost());
            contractOne.setMonthly(fabricContractRecord.getMonthly());
            contractOne.setLicense(fabricContractRecord.getLicense());
            contractOne.setImage(fabricContractRecord.getImage());
            contractOne.setContractDate(fabricContractRecord.getContract_date());
//            contractOne.setType("거래이력");

            result.add(contractOne);
        }

        for (int i = 0; i < statuses.size(); i++) {
            FabricStatusRecord fabricStatusRecord = new FabricStatusRecord();
            FabricResponseDto statusOne = new FabricResponseDto();

            fabricStatusRecord = iFabricCCService.queryStatus("STATUS" + statuses.get(i).getStatusId());

            System.out.println(fabricStatusRecord.toString());

            statusOne.setContractId(fabricStatusRecord.getStatus_id());
            statusOne.setAddress(fabricStatusRecord.getAddress());
//            statusOne.setSd(fabricStatusRecord.getSd());
//            statusOne.setSgg(fabricStatusRecord.getSgg());
//            statusOne.setEmd(fabricStatusRecord.getEmd());
            statusOne.setLongitude(fabricStatusRecord.getLongitude());
            statusOne.setLatitude(fabricStatusRecord.getLatitude());
            statusOne.setFloor(fabricStatusRecord.getFloor());
            statusOne.setHo(fabricStatusRecord.getHo());
            statusOne.setKind(fabricStatusRecord.getCategory());
            statusOne.setDetail(fabricStatusRecord.getDetail());
            statusOne.setCost(fabricStatusRecord.getCost());
            statusOne.setLicense(fabricStatusRecord.getLicense());
            statusOne.setImage(fabricStatusRecord.getImage());
            statusOne.setExclusive(fabricStatusRecord.getExclusive());
            statusOne.setContractDate(fabricStatusRecord.getStart_date());
            statusOne.setEndDate(fabricStatusRecord.getEnd_date());
//            statusOne.setType("상태이력");

            result.add(statusOne);
        }


        for (int i = 0; i < result.size(); i++){
            long temp = 0;
            String addr = result.get(i).getAddress();
            System.out.println("========== 시작 =============");
            Long aroundId = aroundService.findByAddress(addr);
            System.out.println("ID : " + aroundId);
            System.out.println("around clear !!!");

            long score = 0;
            if(favoriteService.findByAroundIdInFavorite(aroundId) != null){
                score = favoriteService.findByAroundId(aroundId);
                System.out.println(" ------- favorie clear !!! --------");
            }

            if(score > 0)
                temp = score;
            result.get(i).setIsLike(String.valueOf(temp));
        }

        System.out.println("--- sort 시작 ----");
        Collections.sort(result, new Comparator<FabricResponseDto>() {

            @Override
            public int compare(FabricResponseDto o1, FabricResponseDto o2) {
                return o2.getContractDate().compareTo(o1.getContractDate());
            }
        });

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).toString());
        }
        System.out.println("result Size :  " + result.size());
        return result;
    }

    /**
     *
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
            FabricContractRecord fabricContractRecord = new FabricContractRecord();
            fabricContractRecord = iFabricCCService.queryContract("CONTRACT" + num);
            return fabricContractRecord;
        } else {
            FabricStatusRecord fabricStatusRecord = new FabricStatusRecord();
            fabricStatusRecord = iFabricCCService.queryStatus("STATUS" + num);
            StatusConfirmDto statusConfirmDto = new StatusConfirmDto(fabricStatusRecord);
            return statusConfirmDto;
        }
    }

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