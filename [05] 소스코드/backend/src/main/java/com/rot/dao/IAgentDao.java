package com.rot.dao;

import com.rot.model.Agent;

public interface IAgentDao {
	Agent getAgent(Agent agent);

	int createAgent(Agent agent);

	int updateAgent(Agent agent);

	int deleteAgent(long num);

	int pointUp(String license);

	int reportUp(long num);

	String getLicense(long num);

	int getPoint(String license);

	Agent getAgentByNum(long num);

	int countUp(String agentNum);

	int updateImage(Agent agent);

	Agent getAgentByLicense(String license);
}
