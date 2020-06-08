package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.dto.StatusSaveRequestDto;
import com.roomoftruth.rot.fabric.FabricStatusRecord;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.service.AgentService;
import com.roomoftruth.rot.service.StatusService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StatusController {

    private final StatusService statusService;
    private final IFabricCCService iFabricCCService;
    private final AgentService agentService;

    static int status_idx = 1;

    /**
     *
     * @param statusSaveRequestDto
     * @return statusID
     */
    @PostMapping("/status/save")
    @ApiOperation("패브릭과 DB에 유지보수 이력 저장")
    public ResponseEntity<Object> save(@RequestBody @Valid StatusSaveRequestDto statusSaveRequestDto) {

        if (statusSaveRequestDto.getAddress().equals("string")) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        statusSaveRequestDto.setSd("-");
        statusSaveRequestDto.setSgg("-");
        statusSaveRequestDto.setEmd("-");

        String PK = "TESTINGS" + status_idx;
        System.out.println("PK : " + PK);

        FabricStatusRecord fabricStatusRecord = new FabricStatusRecord(statusSaveRequestDto);
        fabricStatusRecord.setStatus_id(PK);

        System.out.println("원장에 데이터 저장 시작");
        System.out.println(fabricStatusRecord.toString());
        boolean result = iFabricCCService.registerStatus(fabricStatusRecord);

        if (result == true) {
            System.out.println("원장 저장 성공");

            fabricStatusRecord.setStatus_id(String.valueOf(status_idx));

            if (statusService.saveStatus(fabricStatusRecord) == status_idx) {
                System.out.println("DB 저장 성공");
                status_idx++;
                agentService.pointUp(statusSaveRequestDto.getLicense());

                return new ResponseEntity<Object>(String.valueOf(PK), HttpStatus.OK);
            } else {
                System.out.println("DB 저장 실패 !! ");
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }
        } else {
            System.out.println("원장 저장 실패 !! ");
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }
}
