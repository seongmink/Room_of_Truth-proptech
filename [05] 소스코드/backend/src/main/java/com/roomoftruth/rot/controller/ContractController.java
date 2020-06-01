package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;




//    @PostMapping("/building")
//    @ApiOperation("패브릭과 DB에 건물 정보 저장")
//    public ResponseEntity<Object> registBuildFabric(@RequestBody Building building) throws IOException {
//        logger.info("POST : /api/building");
//
//        if(building.getAddress().equals("string")) {
//            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//        }
//
//        // PK로 사용할 num 값 DB로부터 받아오기
//        int num = buildingService.getLastCount();
//        String fbNum = "BB" + num;
//
//        // 이미지 이름 변경
//        if (building.getImage() == null || building.getImage().equals("")) {
//            building.setImage("default.png");
//        } else {
//            building.setImage(FileUploadDownloadService.newFileName(building.getImage()));
//        }
//
//        FabricRecord fb = new FabricRecord();
//        logger.info("Request Building Data : " + building.toString());
//
//        fb.setNum(fbNum);
//        fb.setAddress(building.getAddress());
//        fb.setDong(building.getDong());
//        fb.setHo(building.getHo());
//        fb.setLatitude(building.getLatitude());
//        fb.setLongitude(building.getLongitude());
//        fb.setSupply(building.getSupply());
//        fb.setExclusive(building.getExclusive());
//        fb.setDetails(building.getDetails());
//        fb.setCost(building.getCost());
//        fb.setStartDate(building.getStartDate());
//        fb.setEndDate(building.getEndDate());
//        fb.setName(building.getName());
//        fb.setLicense(building.getLicense());
//        fb.setImage(building.getImage());
//        fb.setCreatedAt("0");
//        fb.setExpiredAt("0");
//
//        // 빈 데이터에 대한 에러처리
//        if (fb.getEndDate() == null || fb.getEndDate().equals("")) {
//            fb.setEndDate("-");
//        }
//
//        if (fb.getDong() == null || fb.getDong().equals("")) {
//            fb.setDong("-");
//        }
//
//        if (fb.getHo() == null || fb.getHo().equals("")) {
//            fb.setHo("-");
//        }
//
//        logger.info("원장 저장 시작");
//        logger.info("원장에 저장할 데이터 : " + fb.toString());
//        boolean result = fabricService.registerBuildingInfo(fb);
//        if (result == true) {
//            // 디비저장해주자
//            logger.info("원장 저장 성공");
//            if (building.getDong() == null) {
//                building.setDong("");
//            }
//            // <createBuilding> Test 성공했음 !, 04-27
//            if (buildingService.createBuilding(building) == 0) {
//                logger.debug("createBuilding failed");
//                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//            }
//
//            // <countUp> xml 확인했음 !, 04-27
//            if (agentService.countUp(building.getLicense()) == 0) {
//                logger.debug("countUp failed");
//                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//            }
//
//            // <pointUp> xml 확인했음 !, 04-27
//            if (agentService.pointUp(building.getLicense()) == 0) {
//                logger.debug("pointUp failed");
//                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//            }
//            logger.info("DB 저장 성공");
//            return new ResponseEntity<Object>(String.valueOf(building.getNum()), HttpStatus.OK);
//        } else {
//            logger.info("원장 저장 실패!!");
//            logger.info("실패한 등록 빌딩 데이터 : " + fb.toString());
//
//            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping("/building")
//    @ApiOperation("프론트에서 건물 이력 정보 받기")
//    public List<Building> getAllBuilding() throws IOException {
//        logger.info("GET : /api/building");
//
//        List<Building> result = new ArrayList<>();
//        HashSet<Building> set = new HashSet<>();
//
//        List<Building> bTemp = buildingService.getAllBuilding();
//        List<Maintenance> mTemp = maintenanceService.getAllMaintenance();
//
//        for (int i = 0; i < bTemp.size(); i++) {
//            Building b = new Building();
//            b.setAddress(bTemp.get(i).getAddress());
//            b.setDong(bTemp.get(i).getDong());
//            b.setHo(bTemp.get(i).getHo());
//            b.setLatitude(bTemp.get(i).getLatitude());
//            b.setLongitude(bTemp.get(i).getLongitude());
//            set.add(b);
//        }
//
//        for (int i = 0; i < mTemp.size(); i++) {
//            Building b = new Building();
//            b.setAddress(mTemp.get(i).getAddress());
//            b.setDong(mTemp.get(i).getDong());
//            b.setHo(mTemp.get(i).getHo());
//            b.setLatitude(mTemp.get(i).getLatitude());
//            b.setLongitude(mTemp.get(i).getLongitude());
//            set.add(b);
//        }
//
//        Iterator<Building> it = set.iterator();
//
//        while(it.hasNext()) {
//            result.add(it.next());
//        }
//
//        return result;
//    }

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
