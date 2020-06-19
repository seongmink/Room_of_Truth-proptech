package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.fabric.IFabricCCService;
import com.roomoftruth.rot.jwt.JwtService;
import com.roomoftruth.rot.service.AgentService;
import com.roomoftruth.rot.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AgentController {

    private final UserService userService;
    private final AgentService agentService;
    private final JwtService jwtService;
    private final IFabricCCService iFabricCCService;

    @PostMapping("/agent/check")
    @ApiOperation("공인중개사 존재 여부 확인")
    public String checkAgentLicense(@RequestParam String license) {
        log.info("AgentController : checkAgentLicense / {}", license);

        return agentService.checkAgentLicense(license);
    }

    @PostMapping("/agent")
    @ApiOperation("공인중개사 등록")
    public String createAgent(@RequestBody AgentSaveRequestDto requestDto) {
        log.info("AgentController : createAgent / {}", requestDto.getUserNum());

        agentService.save(requestDto);

        User user = userService.findByNum(requestDto.getUserNum());
        UserResponseDto userResponseDto = new UserResponseDto(user);

        String jwt = jwtService.create("user", userResponseDto, "user");

        return jwt;
    }

    @GetMapping("/agent/ranking")
    @ApiOperation("랭킹 가져오기")
    public List<AgentRankingResponseDto> getRanking() {
        log.info("AgentController : getRanking");

        agentService.updateRanking();

        return agentService.getRanking();
    }

    @GetMapping("/agent/detail/{num}")
    @ApiOperation("공인중개사 상세 조회")
    public AgentDetailResponseDto getAgentDetail(@PathVariable long num) {
        log.info("AgentController : getAgentDetail");

        return agentService.getAgentDetail(num);
    }

    /**
     * license 1개를 입력받아서 ---> 등록한 모든 이력 보여주기 ( 주소, 층, 호, image, type, date )
     * @param num
     * @return
     */
//    @GetMapping("/agent/contribution/{num}")
//    @ApiOperation("공인중개사가 등록한 건물 조회")
//    public List<ContributionResponseDto> getAgentContribution(@PathVariable long num) {
//        log.info("AgentController : getAgentContribution");
//
//        return agentService.getAgentContribution(num);
//    }

}
