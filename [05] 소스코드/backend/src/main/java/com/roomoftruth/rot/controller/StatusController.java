//package com.roomoftruth.rot.controller;
//
//import com.roomoftruth.rot.fabric.IFabricCCService;
//import com.roomoftruth.rot.service.AgentService;
//import com.roomoftruth.rot.service.StatusService;
//import com.roomoftruth.rot.util.AddressChangeUtil;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@Slf4j
//public class StatusController {
//
//    private final StatusService statusService;
//    private final IFabricCCService iFabricCCService;
//    private final AgentService agentService;
//
//    static int status_idx = 6;
//
//    /**
//     *
//     * @param statusSaveRequestDto
//     * @return statusID
//     */
//    @PostMapping("/status/save")
//    @ApiOperation("패브릭과 DB에 유지보수 이력 저장")
//    public String save(@RequestBody @Valid StatusSaveRequestDto statusSaveRequestDto) {
//
//        // 시도 Address Util Change
//        String[] addr = statusSaveRequestDto.getAddress().split(" ");
//        AddressChangeUtil addressChangeUtil = new AddressChangeUtil();
//        addr[0] = addressChangeUtil.addressChange(addr[0]);
//
//        StringBuilder sb = new StringBuilder();
//        for(int i= 0; i < addr.length; i++){
//            sb.append(addr[i]+" ");
//        }
//
//        statusSaveRequestDto.setAddress(sb.toString().trim());
//        statusSaveRequestDto.setSd(addr[0]);
//
//        statusSaveRequestDto.setSgg("-");
//        statusSaveRequestDto.setEmd("-");
//        statusSaveRequestDto.setExclusive("-");
//        if (statusSaveRequestDto.getCategory() == "" ||
//                statusSaveRequestDto.getCategory().equals("")) {
//            statusSaveRequestDto.setCategory("-");
//        }
//
//        String PK = "STATUS" + status_idx;
//
//        FabricStatusRecord fabricStatusRecord = new FabricStatusRecord(statusSaveRequestDto);
//        fabricStatusRecord.setStatus_id(PK);
//
//        boolean result = iFabricCCService.registerStatus(fabricStatusRecord);
//
//        if (result == true) {
//            fabricStatusRecord.setStatus_id(String.valueOf(status_idx));
//
//            System.out.println(statusService.saveStatus(fabricStatusRecord));
//
//            if (true) {
//                status_idx++;
//                agentService.pointUp(statusSaveRequestDto.getLicense());
//
//                return String.valueOf(status_idx - 1);
//            } else {
//                return "DB 저장 실패 !!";
//            }
//        } else {
//            return "원장 저장 실패 !!";
//        }
//    }
//}
