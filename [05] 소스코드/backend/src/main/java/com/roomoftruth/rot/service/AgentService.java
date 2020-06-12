package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.AgentDetailResponseDto;
import com.roomoftruth.rot.dto.AgentRankingResponseDto;
import com.roomoftruth.rot.dto.AgentSaveRequestDto;
import com.roomoftruth.rot.dto.ContributionResponseDto;
import com.roomoftruth.rot.repository.AgentRepository;
import com.roomoftruth.rot.repository.ContractRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AgentService {

	private final AgentRepository agentRepository;
	private final UserRepository userRepository;
	private final ContractRepository contractRepository;
//	private final StatusRepository statusRepository;

	public String checkAgentLicense(String license) {

		Agent agent = agentRepository.findByLicense(license);

		if(agent != null) { // 없으면 등록 가능
			if(license.equals("SSAFY-대전-006")) {
				return "success";
			}
			return "failed";
//			return "success";
		}

		return "failed";
	}

	@Transactional
	public Long save(AgentSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());
		user.updateAuth();

		return agentRepository.save(requestDto.toEntity(user)).getUser().getNum();
	}

	@Transactional
	public void updateRanking() {

		List<Agent> agents = agentRepository.getRanking();

		int rnk = 1;
		for (Agent a : agents) {

			a.updateRanking(rnk);
			rnk++;
		}
	}

	@Transactional
	public void pointUp(String license) {
		agentRepository.pointUp(license);
	}

	public List<AgentRankingResponseDto> getRanking() {

		List<Agent> ranking = agentRepository.getRankingTop9();
		List<AgentRankingResponseDto> result = new ArrayList<>();

		for (int i = 0; i < ranking.size(); i++) {
			result.add(new AgentRankingResponseDto(ranking.get(i)));
			if(result.get(i).getAgentPicture() == null || result.get(i).getAgentPicture().equals("null")) { // 기본 이미지 처리
				result.get(i).updateDefaultImage();
			}
		}

		return result;
	}

	public AgentDetailResponseDto getAgentDetail(long num) {

		Agent agent = agentRepository.getAgentByUserNum(num);

		AgentDetailResponseDto result = new AgentDetailResponseDto(agent);

		return result;
	}

//	public List<ContributionResponseDto> getAgentContribution(long num) {
//
//		String license = agentRepository.getAgentByUserNum(num).getLicense();
//
//		List<ContributionResponseDto> result = new ArrayList<>();
//
//		List<Contract> contractList = contractRepository.findTop1000AllByLicenseOrderByContractDate(license);
//		List<Status> statusList = statusRepository.findTop1000AllByLicenseOrderByStartDate(license);
//
//		for (Contract c : contractList) {
//			result.add(new ContributionResponseDto(c));
//		}
//
//		for (Status s : statusList) {
//			result.add(new ContributionResponseDto(s));
//		}
//
//		Collections.sort(result, new Comparator<ContributionResponseDto>() {
//			@Override
//			public int compare(ContributionResponseDto o1, ContributionResponseDto o2) {
//				return o2.getDate().compareTo(o1.getDate());
//			}
//		});
//
//		return result;
//	}

}
