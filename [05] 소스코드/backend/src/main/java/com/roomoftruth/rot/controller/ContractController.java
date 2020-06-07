package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Around;
import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.fabric.FabricContractRecord;
import com.roomoftruth.rot.fabric.FabricStatusRecord;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.service.AroundService;
import com.roomoftruth.rot.service.ContractService;
import com.roomoftruth.rot.service.StatusService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/buildings")
    @ApiOperation("계약 이력 등록하기")
    public ResponseEntity<Object> save(@RequestBody @Valid ContractSaveRequestDto contractSaveRequestDto){
        System.out.println("=== POST : /api/building ====");
        long result = contractService.saveContract(contractSaveRequestDto);
        System.out.println("등록 ID : " + result);
        return new ResponseEntity<Object>(String.valueOf(result), HttpStatus.OK);
    }

    /**
     *    Around 주소에 대한 모든 위도, 경도와 함께 출력
     *    return (addressm latitude, longitude)
     */
    @GetMapping("/building")
    @ApiOperation("조회하기에 모든 이력 뿌려주기")
    public List<ContractFindLocationDto> getAllContracts(@RequestParam String city, @RequestParam String local){
        System.out.println("=== GET : /api/building ====");

        String key = city + " " + local;
        List<Around> allAddress = aroundService.findAllAddress(key);

        List<ContractFindLocationDto> result = contractService.findContractLocations(key);

        return result;

    }

    /**
     * 위도, 경도로 모든 이력 조회
     * @return contact_id, address, floor, ho, latitude, longitude, image
     */
    @PostMapping("/building/details")
    @ApiOperation("건물 상세 정보 뿌려주기")
    public List<Contract> getAllDetails(@RequestBody ContractFindRequestDto[] requestDto){
        System.out.println("====== POST : api/details");
        String sd = requestDto[0].getSd();
        String sgg = requestDto[0].getSgg();
        String key = sd + " " + sgg;

        // 시도, 시군구로 모든 이력 찾기
        List<Contract> searchData = contractService.findAllByAddressContaining(key);

        // 요청받은 위도, 경도(requestDto)에 맞는 이력 찾아주기
        List<Contract> result = new ArrayList<>();

        result = contractService.getAllDetails(requestDto, searchData);

        return result;
    }


    /**
     * param -> return Passed
     * @param contractFindRequestDto
     * @return contract.Image
     */
//    @PostMapping("findImage")
//    public String findImage(@RequestBody ContractFindRequestDto contractFindRequestDto){
//        String result = contractService.getContractImage(contractFindRequestDto);
//        System.out.println("image : " + result);
//        return result;
//    }

/**
 * 구 현 해 야 됨 !
 */

    @PostMapping("/building/detail/block")
    @ApiOperation("건물 블록 정보 뿌려주기")
    public List<FabricResponseDto> getBuildingDetail(@RequestBody FabricResponseDto request) throws IOException {
        System.out.println("POST : /api/building/detail/block");

        List<Contract> contracts = new ArrayList<Contract>();
        List<Status> statuses = new ArrayList<Status>();

        // Address, Dong, Ho가 같은 모든 계약 이력 찾아옴
        String address = request.getAddress();
        String floor = request.getFloor();
        String ho = request.getHo();
        contracts = contractService.findAllByAddressAndFloorAndHo(address, floor, ho);

        // Address, Dong, Ho가 같은 모든 유지보수 이력 찾아옴
        statuses = statusService.findAllByAddressAndFloorAndHo(address, floor, ho);

        // 이제 각각의 ID를 뽑아서 Fabric에 가서 해당하는 상세 정보를 하나씩 뽑아온다.
        List<FabricResponseDto> result = new ArrayList<>();
        FabricResponseDto fabricResponseDto = new FabricResponseDto();

        // BuildingInfo 불러오기
        for (int i = 0; i < contracts.size(); i++) {
            FabricContractRecord fabricContractRecord = new FabricContractRecord();
            FabricResponseDto contractOne = new FabricResponseDto();

            // "TEST+contract_id"에 해당하는 이력 1개 찾아옴
            fabricContractRecord = iFabricCCService.queryContract("TEST00" + contracts.get(i).getContractId());

            // 찾아온 fabricContractRecord를 FabricResponseDto로 데이터 복사
            contractOne.setContractId(fabricContractRecord.getContract_id());
            contractOne.setAddress(fabricContractRecord.getAddress());
            contractOne.setSd(fabricContractRecord.getSd());
            contractOne.setSgg(fabricContractRecord.getSgg());
            contractOne.setEmd(fabricContractRecord.getEmd());
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
            contractOne.setType("거래이력");

            // result에 넣어주기
            result.add(contractOne);
        }

        // StatusInfo 불러오기
        for (int i = 0; i < statuses.size(); i++) {
            FabricStatusRecord fabricStatusRecord = new FabricStatusRecord();
            FabricResponseDto statusOne = new FabricResponseDto();

            // "TEST+status_id"에 해당하는 이력 1개 찾아옴
            fabricStatusRecord = iFabricCCService.queryStatus("TSS00" + statuses.get(i).getStatusId());

            // 찾아온 fabricStatusRecord를 FabricResponseDto로 데이터 복사
            statusOne.setContractId(fabricStatusRecord.getStatus_id());
            statusOne.setAddress(fabricStatusRecord.getAddress());
            statusOne.setSd(fabricStatusRecord.getSd());
            statusOne.setSgg(fabricStatusRecord.getSgg());
            statusOne.setEmd(fabricStatusRecord.getEmd());
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
            statusOne.setContractDate(fabricStatusRecord.getContract_date());
            statusOne.setEndDate(fabricStatusRecord.getEnd_date());
            statusOne.setType("상태이력");

            // result에 넣어주기
            result.add(statusOne);
        }

        Collections.sort(result, new Comparator<FabricResponseDto>() {

            @Override
            public int compare(FabricResponseDto o1, FabricResponseDto o2) {
                return o2.getContractDate().compareTo(o1.getContractDate());
            }
        });

        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i).toString());
        }

        return result;
    }

    /**
     * DB에 있는 데이터 Fabric 원장에 등록하기
     */
    @GetMapping("/dataTransfer")
    @ApiOperation("원장에 데이터 등록하기 ~999")
    public void dataTransfer(){
        contractService.dataTransfer();
    }

    // Channel Load
    @GetMapping("/loadchannel")
    @ApiOperation("채널 한번 로드하기")
    public boolean loadChannel() throws IOException {
        return iFabricCCService.loadChannel();
    }
}

