package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.Contract;
import com.roomoftruth.rot.domain.Status;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.*;
import com.roomoftruth.rot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AgentService {

	private final AgentRepository agentRepository;
	private final UserRepository userRepository;
	private final ContractContributionRepository contractContributionRepository;
	private final StatusContributionRepository statusContributionRepository;

	public String checkAgentLicense(String license) {

		Agent agent = agentRepository.findByLicense(license);

		if(agent != null) {
			return "success";
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
		System.out.println(result.toString());
		return result;
	}

	public List<Object> getAgentContribution(long userNum) {
		List<Object> result = new ArrayList<>();

		List<ContributionContractResponseDto> contracts = contractContributionRepository.findByLicense(userNum);
		List<ContributionStatusResponseDto> statuses = statusContributionRepository.findByLicense(userNum);

		for (ContributionContractResponseDto c : contracts) {
			result.add(c);
		}

		for (ContributionStatusResponseDto s : statuses) {
			result.add(s);
		}

		System.out.println(result.size());
		return result;
	}

}
