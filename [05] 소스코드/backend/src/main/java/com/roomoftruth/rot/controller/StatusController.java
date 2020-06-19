package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.contracts.StatusSaveRequestDto;
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
    private final AgentService agentService;
    private final AroundService aroundService;

    static int status_idx = 1;

    /**
     * @param statusSaveRequestDto
     * @return statusID
     */
    @PostMapping("/status/save")
    @ApiOperation("패브릭과 DB에 유지보수 이력 저장")
    public String save(@RequestBody @Valid StatusSaveRequestDto statusSaveRequestDto) {
        System.out.println("====== POST : api/status/save");

        // 시도 Address Util Change
        String[] addr = statusSaveRequestDto.getAddress().split(" ");
        AddressChangeUtil addressChangeUtil = new AddressChangeUtil();
        addr[0] = addressChangeUtil.addressChange(addr[0]);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addr.length; i++) {
            sb.append(addr[i] + " ");
        }

        statusSaveRequestDto.setAddress(sb.toString().trim());

        // 주소로 address_id 값 찾아오기
        long aroundId = 0;

        if (aroundService.findTop1ByAddress(statusSaveRequestDto.getAddress()) == null) {
            System.out.println("Around 정보가 없는 데이터 입니다.");
            aroundId = -1;
            return String.valueOf(aroundId);
        } else {
            aroundId = aroundService.findTop1ByAddress(statusSaveRequestDto.getAddress()).getAroundId();
        }

        Status status = new Status(status_idx, aroundId, statusSaveRequestDto);

        if (statusService.saveStatus(status) == status_idx) {
            System.out.println("DB 저장 성공");
            status_idx++;
            agentService.pointUp(status.getLicense());
            System.out.println("point Up 성공");
            return String.valueOf(status_idx - 1);
        } else {
            return "DB 저장 실패";
        }
    }
}
