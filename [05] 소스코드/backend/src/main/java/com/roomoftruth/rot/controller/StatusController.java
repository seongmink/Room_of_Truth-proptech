package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.dto.contracts.StatusSaveRequestDto;
import com.roomoftruth.rot.service.AgentService;
import com.roomoftruth.rot.service.AroundService;
import com.roomoftruth.rot.service.StatusService;
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

    /**
     * @param statusSaveRequestDto
     * @return statusID
     */
    @PostMapping("/status/save")
    @ApiOperation("DB에 유지보수 이력 저장")
    public String save(@RequestBody @Valid StatusSaveRequestDto statusSaveRequestDto) {
        System.out.println("====== POST : api/status/save");

        // 주소로 address_id 값 찾아오기
        long aroundId = aroundService.findTop1ByAddress(statusSaveRequestDto.getAddress()).getAroundId();

        Status status = new Status(aroundId, statusSaveRequestDto);
        Status result = statusService.saveStatus(status);
        agentService.pointUp(status.getLicense());
        return String.valueOf(result.getStatusId());
    }
}
