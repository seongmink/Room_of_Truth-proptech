package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.fabric.StatusSaveRequestDto;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.fabric.StatusRecord;
import com.roomoftruth.rot.service.AgentService;
import com.roomoftruth.rot.service.AroundService;
import com.roomoftruth.rot.service.StatusService;
import com.roomoftruth.rot.util.AddressChangeUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final AroundService aroundService;

    static int status_idx = 2;

    /**
     *
     * @param statusSaveRequestDto
     * @return statusID
     */
    @PostMapping("/status/save")
    @ApiOperation("패브릭과 DB에 유지보수 이력 저장")
    public String save(@RequestBody @Valid StatusSaveRequestDto statusSaveRequestDto) {
        System.out.println("Request :: " + statusSaveRequestDto.toString());
        // 시도 Address Util Change
        String[] addr = statusSaveRequestDto.getAddress().split(" ");
        AddressChangeUtil addressChangeUtil = new AddressChangeUtil();
        addr[0] = addressChangeUtil.addressChange(addr[0]);

        StringBuilder sb = new StringBuilder();
        for(int i= 0; i < addr.length; i++){
            sb.append(addr[i]+" ");
        }

        statusSaveRequestDto.setAddress(sb.toString().trim());

        String PK = "TEST_S" + status_idx;

        // 주소로 address_id 값 찾아오기
        long aroundId = 0;

        if(aroundService.findTop1ByAddress(statusSaveRequestDto.getAddress()) == null){
            // 1. AddressID -1 설정 후 반환
            System.out.println("Around 정보가 없는 데이터 입니다.");
            aroundId = -1;
            return String.valueOf(aroundId);
        } else {
            aroundId = aroundService.findTop1ByAddress(statusSaveRequestDto.getAddress()).getAroundId();
        }

        // 블록체인에 넣을 StatusRecord 만들어서 요청 날리자
        StatusRecord statusRecord = new StatusRecord(PK, aroundId, statusSaveRequestDto);

        System.out.println("-- 넣기 직전 : " + statusRecord);
        boolean result = iFabricCCService.registerStatus(statusRecord);

        if (result == true) {
            System.out.println("원장 저장 성공");
            statusRecord.setStatus_id(String.valueOf(status_idx));
            Status saveStatus = new Status(statusRecord);

            if(statusService.saveStatus(saveStatus) == status_idx){
                System.out.println("DB 저장 성공");
                status_idx++;
                agentService.pointUp(statusRecord.getLicense());
                System.out.println("point Up 성공");
                return String.valueOf(status_idx - 1);
            } else {
                return "DB 저장 실패";
            }
        } else {
            return "실패";
        }
    }
}
