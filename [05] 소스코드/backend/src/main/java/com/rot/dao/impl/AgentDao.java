package com.rot.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rot.dao.IAgentDao;
import com.rot.model.Agent;

@Repository
public class AgentDao implements IAgentDao {

	private String ns = "agent.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Agent getAgent(Agent agent) {
		return sqlSession.selectOne(ns + "getAgent", agent);
	}

	@Override
	public int createAgent(Agent agent) {
		return sqlSession.insert(ns + "createAgent", agent);
	}

	@Override
	public int updateAgent(Agent agent) {
		return sqlSession.update(ns + "updateAgent", agent);
	}

	@Override
	public int deleteAgent(long num) {
		return sqlSession.delete(ns + "deleteAgent", num);
	}

	@Override
	public int pointUp(String license) {
		return sqlSession.update(ns + "pointUp", license);
	}

	@Override
	public int reportUp(long num) {
		return sqlSession.update(ns + "reportUp", num);
	}

	@Override
	public String getLicense(long num) {
		return sqlSession.selectOne(ns + "getLicense", num);
	}

	@Override
	public int getPoint(String license) {
		return sqlSession.selectOne(ns + "getPoint", license);
	}

	@Override
	public Agent getAgentByNum(long num) {
		return sqlSession.selectOne(ns + "getAgentByNum", num);
	}

	@Override
	public int countUp(String agentNum) {
		return sqlSession.update(ns + "countUp", agentNum);
	}

	@Override
	public int updateImage(Agent agent) {
		return sqlSession.update(ns + "updateImage", agent);
	}

	@Override
	public Agent getAgentByLicense(String license) {
		return sqlSession.selectOne(ns + "getAgentByLicense", license);
	}

}
