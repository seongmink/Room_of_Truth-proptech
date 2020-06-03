package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.ContractFindRequestDto;
import com.roomoftruth.rot.dto.ContractFindResponseDto;
import com.roomoftruth.rot.dto.ContractResultDto;
import com.roomoftruth.rot.dto.ContractSaveRequestDto;
import com.roomoftruth.rot.service.ContractService;
import com.roomoftruth.rot.service.StatusService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContractController {

    private final ContractService contractService;
    private final StatusService statusService;

    @PostMapping("/buildings")
    @ApiOperation("계약 이력 등록하기")
    public ResponseEntity<Object> save(@RequestBody @Valid ContractSaveRequestDto requestDto){
        System.out.println("=== POST : /api/building ====");
        long result = contractService.saveContract(requestDto);

        return new ResponseEntity<Object>(String.valueOf(result), HttpStatus.OK);
    }

    @GetMapping("/building")
    @ApiOperation("조회하기에 모든 이력 뿌려주기")
    public List<FindAllContract> getAllContracts(){
        System.out.println("=== GET : /api/building ====");

        List<FindAllContract> result = new ArrayList<>();
        HashSet<FindAllContract> set = new HashSet<>();

        List<Contract> contractTemp = contractService.findAll();
        List<Status> statusTemp = statusService.findAll();

        for(int i = 0; i < contractTemp.size(); i++){
            FindAllContract findAllContract = new FindAllContract();
            Contract temp = contractTemp.get(i);
            findAllContract.setAddress(temp.getAddress());
            findAllContract.setFloor(temp.getFloor());
            findAllContract.setHo(temp.getHo());
            findAllContract.setLatitude(temp.getLatitude());
            findAllContract.setLongitude(temp.getLongitude());
            set.add(findAllContract);
        }

        for(int i = 0; i < statusTemp.size(); i++){
            FindAllContract findAllContract = new FindAllContract();
            Status temp = statusTemp.get(i);
            findAllContract.setAddress(temp.getAddress());
            findAllContract.setFloor(temp.getFloor());
            findAllContract.setHo(temp.getHo());
            findAllContract.setLatitude(temp.getLatitude());
            findAllContract.setLongitude(temp.getLongitude());
            set.add(findAllContract);
        }

        Iterator<FindAllContract> it = set.iterator();

        while(it.hasNext()){
            result.add(it.next());
        }
        return result;
    }

    @Data
    static class FindAllContract {
        private String address;
        private String floor;
        private String ho;
        private String latitude;
        private String longitude;
    }

    @PostMapping("details")
    @ApiOperation("건물 상세 정보 뿌려주기")
    public List<ContractResultDto> getAllDetails(@RequestBody Contract[] contracts){
        System.out.println("====== POST : api/v1/details");
        List<ContractResultDto> data = contractService.findAllDetails(contracts);


        System.out.println("------------실행 성공??-----------");
        for (ContractResultDto contractResultDto : data) {
            System.out.println((contractResultDto.getAddress()));
        }
        return data;
    }

    /**
     * param -> return Passed
     * @param contractFindRequestDto
     * @return contract.Image
     */
    @PostMapping("findImage")
    public String findImage(@RequestBody ContractFindRequestDto contractFindRequestDto){
        String result = contractService.getContractImage(contractFindRequestDto);
        System.out.println("image : " + result);
        return result;
    }

/**
 * 구 현 해 야 됨 !
 */

//    @PostMapping("/building/detail") // 모달에서는 이미지가 필요함 ^^
//    @ApiOperation("건물 상세 정보 뿌려주기")
//    public List<Building> getBuildingDetail(@RequestBody Building[] building) throws IOException {
//        logger.info("GET : /api/building/detail");
//
//        List<Building> result = new ArrayList<>();
//        HashSet<Building> set = new HashSet<>();
//
//        for (int i = 0; i < building.length; i++) {
//            set.add(building[i]);
//        }
//
//        Iterator<Building> it = set.iterator();
//
//        while (it.hasNext()) {
//
//            // 1개씩 중복없는 위도 경도를 뽑음
//            Building tempBuilding = it.next();
//
//            // 쿼리날려서 해당 위도 경도의 중복 없는 리스트를 뽑아옴
//            List<Building> save = buildingService.getDetailList(tempBuilding);
//
//            // 그다음 우선 빌딩부터 이미지를 찾아보자!!
//            for (int i = 0; i < save.size(); i++) {
//
//                String image = buildingService.getBImage(save.get(i));
//                // 이미지가 없네? 그러면 메인턴스로 가서 조회해!
//                if (image == null) {
//                    String mimage = buildingService.getMImage(save.get(i));
//                    save.get(i).setImage("images/"+mimage);
//                } else { // 이미지가 있네? 이걸쓰면 됨^^
//                    save.get(i).setImage("images/"+image);
//                }
//                Building b = save.get(i);
//                result.add(b);
//            }
//        }
//
//        return result;
//    }

//    @PostMapping("/building/detail/block")
//    @ApiOperation("건물 블록 정보 뿌려주기")
//    public List<Building> getBlockDetail(@RequestBody Building building) throws IOException {
//        logger.info("POST : /api/building/detail/block");
//        // Address , dong , ho 만 사용한다.
//
//        List<Building> result = new ArrayList<>();
//
//        List<Building> bTemp = new ArrayList<Building>();
//        List<Maintenance> mTemp = new ArrayList<Maintenance>();
//
//        // Address, Dong, Ho가 같은 모든 이력 찾아옴
//        bTemp = buildingService.getBuildingNum(building);
//        Maintenance m = new Maintenance();
//        m.setAddress(building.getAddress());
//        m.setDong(building.getDong());
//        m.setHo(building.getHo());
//        mTemp = maintenanceService.getMaintenanceNum(m);
//
//        // BuildingInfo 불러오기
//        for (int i = 0; i < bTemp.size(); i++) {
//            FabricRecord fb = new FabricRecord();
//            Building bd = new Building();
//
//            // Fabric에서 num에 해당하는 Building 정보 찾아옴 ( 1개씩 )
//            fb = fabricService.query("BB" + bTemp.get(i).getNum());
//
//            // fb를 bd로 데이터 복사
//            bd.setNum(fb.getNum());
//            bd.setAddress(fb.getAddress());
//            bd.setDong(fb.getDong());
//            bd.setHo(fb.getHo());
//            bd.setLatitude(fb.getLatitude());
//            bd.setLongitude(fb.getLongitude());
//            bd.setSupply(fb.getSupply());
//            bd.setExclusive(fb.getExclusive());
//            bd.setDetails(fb.getDetails());
//            bd.setCost(fb.getCost());
//            bd.setStartDate(fb.getStartDate());
//            bd.setEndDate(fb.getEndDate());
//            bd.setName(fb.getName());
//            bd.setLicense(fb.getLicense());
//            bd.setImage("images/" + fb.getImage());
//            bd.setType("거래");
//
//            // 빈 데이터에 대한 에러처리
//            if (bd.getEndDate().equals("-")) {
//                bd.setEndDate("");
//            }
//
//            if (bd.getDong().equals("-")) {
//                bd.setDong("");
//            }
//
//            if (bd.getHo().equals("-")) {
//                bd.setHo("");
//            }
//
//            // result에 넣어주기
//            result.add(bd);
//        }

//        // MaintenanceInfo 불러오기
//        for (int i = 0; i < mTemp.size(); i++) {
//            MaintenanceRecord mfb = new MaintenanceRecord();
//            Building bd = new Building();
//
//            // Fabric에서 num에 해당하는 Building 정보 찾아옴 ( 1개씩 )
//            mfb = fabricService.queryMaintenance("MM" + mTemp.get(i).getNum());
//
//            // fb를 bd로 데이터 복사
//            bd.setNum(mfb.getNum());
//            bd.setAddress(mfb.getAddress());
//            bd.setDong(mfb.getDong());
//            bd.setHo(mfb.getHo());
//            bd.setLatitude(mfb.getLatitude());
//            bd.setLongitude(mfb.getLongitude());
//            bd.setCategory(mfb.getCategory());
//            bd.setDetails(mfb.getDetails());
//            bd.setCost(mfb.getCost());
//            bd.setStartDate(mfb.getStartDate());
//            bd.setEndDate(mfb.getEndDate());
//            bd.setLicense(mfb.getLicense());
//            bd.setImage("images/" + mfb.getImage());
//            bd.setType("상태");
//
//            // 빈 데이터에 대한 에러처리
//            if (bd.getEndDate().equals("-")) {
//                bd.setEndDate("");
//            }
//
//            if (bd.getDong().equals("-")) {
//                bd.setDong("");
//            }
//
//            if (bd.getHo().equals("-")) {
//                bd.setHo("");
//            }
//
//            // result에 넣어주기
//            result.add(bd);
//        }
//
//        Collections.sort(result, new Comparator<Building>() {
//
//            @Override
//            public int compare(Building o1, Building o2) {
//                return o2.getStartDate().compareTo(o1.getStartDate());
//            }
//        });
//
//        return result;
//    }
}

