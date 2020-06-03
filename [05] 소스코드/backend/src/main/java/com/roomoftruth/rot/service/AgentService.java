package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.Agent;
import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.AgentRankingResponseDto;
import com.roomoftruth.rot.dto.AgentSaveRequestDto;
import com.roomoftruth.rot.repository.AgentRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AgentService {

	private final AgentRepository agentRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long save(AgentSaveRequestDto requestDto) {

		User user = userRepository.findByNum(requestDto.getUserNum());
		user.updateAuth();

		return agentRepository.save(requestDto.toEntity(user)).getUser().getNum();
	}


	@Transactional
	public List<AgentRankingResponseDto> getRanking() {

//		List<Agent> agents = agentRepository.findAll();
//
//		for (Agent a : agents) {
//			// Agent rnk 찾아서 a.setRnk() 해주기
//
//			a.setRnk(a);
//		}

		List<Agent> ranking = agentRepository.getRankingTop9();

		List<AgentRankingResponseDto> result = new ArrayList<>();
		int rankIndex = 1;
		for (int i = 0; i < ranking.size(); i++) {
			System.out.println(ranking.get(i).getName() + " 랭킹을 " + rankIndex + "로 설정");

			// update
			agentRepository.updateRank(rankIndex, ranking.get(i).getAgentId());
			System.out.println("죽었니??");

//			result.add(new AgentRankingResponseDto(ranking.get(i)));

			if(result.get(i).getAgentPicture() == null || result.get(i).getAgentPicture().equals("null")) { // 기본 이미지 처리
				result.get(i).updateDefaultImage();
			}
			rankIndex++;
		}

		return result;
	}



}
