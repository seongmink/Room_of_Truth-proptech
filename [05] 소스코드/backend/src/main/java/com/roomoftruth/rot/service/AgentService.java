package com.roomoftruth.rot.service;

import com.roomoftruth.rot.domain.User;
import com.roomoftruth.rot.dto.AgentSaveRequestDto;
import com.roomoftruth.rot.repository.AgentRepository;
import com.roomoftruth.rot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


//	public List<AgentRankingResponseDto> getRanking() {
//
//		List<Agent> = agentRepository.
//
//
//		for (int i = 0; i < result.size(); i++) {
//			if(result.get(i).getAPicture() == null || result.get(i).getAPicture().equals("null")) {
//				result.get(i).setAPicture("images/agent_default.png");
//				continue;
//			}
//			result.get(i).setAPicture("images/" + result.get(i).getAPicture());
//		}
//
//		Collections.sort(result, new Comparator<Ranking>() {
//
//			@Override
//			public int compare(Ranking o1, Ranking o2) {
//
//				if(o1.getPoint() == o2.getPoint()) {
//					return o1.getName().compareTo(o2.getName());
//				}
//
//				return o2.getPoint() - o1.getPoint();
//			}
//		});
//
//		return result;
//	}


}
