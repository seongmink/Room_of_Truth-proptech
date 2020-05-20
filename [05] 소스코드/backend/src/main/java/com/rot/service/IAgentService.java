package com.rot.service;

import org.springframework.transaction.annotation.Transactional;

import com.rot.model.Agent;

public interface IAgentService {

	Agent getAgent(Agent agent);

	@Transactional
	int createAgent(Agent agent);

	@Transactional
	int updateAgent(Agent agent);

	@Transactional
	int deleteAgent(long num);

	@Transactional
	int pointUp(String license);

	@Transactional
	int reportUp(long num);

	String getLicnese(long num);

	int getPoint(String license);

	Agent getAgentByNum(long num);

	@Transactional
	int countUp(String agentNum);

	int updateImage(Agent agent);

	Agent getAgentByLicense(String license);

}
