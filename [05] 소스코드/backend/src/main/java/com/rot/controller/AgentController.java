package com.rot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rot.fabric.FabricRecord;
import com.rot.fabric.IFabricCCService;
import com.rot.jwt.JwtService;
import com.rot.model.Agent;
import com.rot.model.Building;
import com.rot.model.User;
import com.rot.service.IAgentService;
import com.rot.service.IBuildingService;
import com.rot.service.IUserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AgentController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IAgentService agentService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IBuildingService buildingService;
	
	@Autowired
	private IFabricCCService fabricService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/agent")
	@ApiOperation("공인중개사 등록")
	public String createAgent(@RequestBody Agent agent) {
		logger.info("POST : /api/agent " + agent.getNum());

		if (agentService.getAgent(agent) == null) {
			logger.info("New Agent : " + agent.getNum());
			agentService.createAgent(agent);
		} else {
			logger.info("Existing Agent : " + agent.getNum());
			agentService.updateAgent(agent);
		}

//		userService.obtainAuth(agent.getNum());
//
//		User user = userService.getUserByNum(agent.getNum());
//
//		String jwt = jwtService.create("user", user, "user");
//
//		return jwt;
		return null;
	}
	
	@PostMapping("/agent/check")
	@ApiOperation("공인중개사 존재 여부 확인")
	public String getAgent(@RequestParam long num, @RequestParam String license) {
		logger.info("POST : /api/agent/check");
		
		return "failed";

//		Agent agentTemp = agentService.getAgentByLicense(license);
//		
//		if(agentTemp == null) {
//			Agent agentTemp2 = agentService.getAgentByNum(num);
//			if(agentTemp2 != null) {
//				return "failed";
//			}
//			return "success";
//		}
//		
//		if(num != agentTemp.getNum()) {
//			return "failed";
//		}
//		
//		return "success";
	}

	@DeleteMapping("/agent/{num}")
	@ApiOperation("공인중개사 삭제")
	public String deleteAgent(@PathVariable("num") long num) {
		logger.info("DELETE : /api/agent/{num} = " + num);

		if (agentService.deleteAgent(num) == 0) {
			logger.error("deleteAgent failed");
			return "deleteAgent failed";
		}
//		if (userService.loseAuth(num) == 0) {
//			logger.error("loseAuth failed");
//			return "loseAuth failed";
//		}
//
//		User user = userService.getUserByNum(num);
//
//		String jwt = jwtService.create("user", user, "user");
//
//		return jwt;
		return null;
	}

	@GetMapping("/agent/{num}")
	@ApiOperation("공인중개사 번호 조회")
	public String getLicense(@PathVariable long num) {
		logger.info("GET : /api/agent/{num} : " + num);

		return agentService.getLicnese(num);
	}

	@GetMapping("/agent/point")
	@ApiOperation("공인중개사 점수 가져오기")
	public int getPoint(@RequestParam String license) {
		logger.info("GET : /api/agent/point");

		return agentService.getPoint(license);
	}

	@GetMapping("/agent/detail/{num}")
	@ApiOperation("공인중개사 상세 조회")
	public Agent getAgentDetail(@PathVariable long num) {
		logger.info("GET : /api/agent/detail");

		Agent agent = agentService.getAgentByNum(num);
		if(agent.getPicture() != null) {
			agent.setPicture("images/" + agent.getPicture());
		} else {
			agent.setPicture("images/agent_default.png");
		}
//		agent.setUPicture(userService.getUserByNum(num).getPicture());
//		agent.setPhoneNum(userService.getUserByNum(num).getPhoneNum());

		return agent;
	}
	
	@GetMapping("/agent/contribution/{num}")
	@ApiOperation("공인중개사가 등록한 건물 조회")
	public List<Building> getAgentContribution(@PathVariable long num) {
		logger.info("GET : /api/agent/contribution/{num} : " + num);

		String license = agentService.getLicnese(num);
		
		List<Building> result = new ArrayList<>();
		
		List<Building> bTemp = buildingService.getAgentContribution(license);
		
		for (int i = 0; i < bTemp.size(); i++) {
			bTemp.get(i).setImage("images/"+bTemp.get(i).getImage());
			result.add(bTemp.get(i));
		}
		
		Collections.sort(result, new Comparator<Building>() {
			@Override
			public int compare(Building o1, Building o2) {
				return o2.getCreatedAt().compareTo(o1.getCreatedAt());
			}
		});
				
		return result;
	}	
	
	@GetMapping("/agent/contribution/detail/{type}/{num}")
	@ApiOperation("공인중개사가 등록한 건물 조회")
	public Building getAgentContributionDetail(@PathVariable(value = "type") int type, @PathVariable long num) {
		logger.info("POST : /api/agent/contribution/detail/{type}/{num}");
		// 계약 = 0, 상태 = 1
		
		Building bd = new Building();
		
		if(type == 0) {
			FabricRecord fb = new FabricRecord();
			fb = fabricService.query("BB" + num);
			
			// fb를 bd로 데이터 복사
			bd.setNum(fb.getNum());
			bd.setAddress(fb.getAddress());
			bd.setDong(fb.getDong());
			bd.setHo(fb.getHo());
			bd.setLatitude(fb.getLatitude());
			bd.setLongitude(fb.getLongitude());
			bd.setSupply(fb.getSupply());
			bd.setExclusive(fb.getExclusive());
			bd.setDetails(fb.getDetails());
			bd.setCost(fb.getCost());
			bd.setStartDate(fb.getStartDate());
			bd.setEndDate(fb.getEndDate());
			bd.setName(fb.getName());
			bd.setLicense(fb.getLicense());
			bd.setImage("images/" + fb.getImage());
			bd.setType("거래");

			// 빈 데이터에 대한 에러처리
			if (bd.getEndDate().equals("-")) {
				bd.setEndDate("");
			}

			if (bd.getDong().equals("-")) {
				bd.setDong("");
			}

			if (bd.getHo().equals("-")) {
				bd.setHo("");
			}
		}
		
		return(bd);
		
	}
	
	public void pointUp(String license) {
		logger.info("METHOD : pointUp");

		agentService.pointUp(license);
	}

	public void reportUp(long num) {
		logger.info("METHOD : reportUp");

		agentService.reportUp(num);
	}

}
