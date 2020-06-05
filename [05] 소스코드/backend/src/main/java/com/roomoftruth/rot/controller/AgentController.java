package com.roomoftruth.rot.controller;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.AgentDetailResponseDto;
import com.roomoftruth.rot.dto.AgentRankingResponseDto;
import com.roomoftruth.rot.dto.AgentSaveRequestDto;
import com.roomoftruth.rot.dto.UserResponseDto;
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

	@PostMapping("/agent/check")
	@ApiOperation("공인중개사 존재 여부 확인")
	public String checkAgentLicense(@RequestParam String license) {
		log.info("AgentController : checkAgentLicense / {}", license);

		if(!(license.equals("대전-SSAFY-001") || license.equals("대전-SSAFY-002") ||
				license.equals("대전-SSAFY-003") || license.equals("대전-SSAFY-004") ||
				license.equals("대전-SSAFY-005"))) {
			return "failed";
		}

		return "success";
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

//
//	@GetMapping("/agent/contribution/{num}")
//	@ApiOperation("공인중개사가 등록한 건물 조회")
//	public List<Building> getAgentContribution(@PathVariable long num) {
//		logger.info("GET : /api/agent/contribution/{num} : " + num);
//
//		String license = agentService.getLicnese(num);
//
//		List<Building> result = new ArrayList<>();
//
//		List<Building> bTemp = buildingService.getAgentContribution(license);
//
//		for (int i = 0; i < bTemp.size(); i++) {
//			bTemp.get(i).setImage("images/"+bTemp.get(i).getImage());
//			result.add(bTemp.get(i));
//		}
//
//		Collections.sort(result, new Comparator<Building>() {
//			@Override
//			public int compare(Building o1, Building o2) {
//				return o2.getCreatedAt().compareTo(o1.getCreatedAt());
//			}
//		});
//
//		return result;
//	}
//
//	@GetMapping("/agent/contribution/detail/{type}/{num}")
//	@ApiOperation("공인중개사가 등록한 건물 조회")
//	public Building getAgentContributionDetail(@PathVariable(value = "type") int type, @PathVariable long num) {
//		logger.info("POST : /api/agent/contribution/detail/{type}/{num}");
//		// 계약 = 0, 상태 = 1
//
//		Building bd = new Building();
//
//		if(type == 0) {
//			FabricRecord fb = new FabricRecord();
//			fb = fabricService.query("BB" + num);
//
//			// fb를 bd로 데이터 복사
//			bd.setNum(fb.getNum());
//			bd.setAddress(fb.getAddress());
//			bd.setDong(fb.getDong());
//			bd.setHo(fb.getHo());
//			bd.setLatitude(fb.getLatitude());
//			bd.setLongitude(fb.getLongitude());
//			bd.setSupply(fb.getSupply());
//			bd.setExclusive(fb.getExclusive());
//			bd.setDetails(fb.getDetails());
//			bd.setCost(fb.getCost());
//			bd.setStartDate(fb.getStartDate());
//			bd.setEndDate(fb.getEndDate());
//			bd.setName(fb.getName());
//			bd.setLicense(fb.getLicense());
//			bd.setImage("images/" + fb.getImage());
//			bd.setType("거래");
//
//			// 빈 데이터에 대한 에러처리
//			if (bd.getEndDate().equals("-")) {
//				bd.setEndDate("");
//			}
//
//			if (bd.getDong().equals("-")) {
//				bd.setDong("");
//			}
//
//			if (bd.getHo().equals("-")) {
//				bd.setHo("");
//			}
//		}
//
//		return(bd);
//
//	}
//
//	public void pointUp(String license) {
//		logger.info("METHOD : pointUp");
//
//		agentService.pointUp(license);
//	}
//
//	public void reportUp(long num) {
//		logger.info("METHOD : reportUp");
//
//		agentService.reportUp(num);
//	}
//
//}

}
