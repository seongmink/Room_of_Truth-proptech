package com.rot.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rot.dao.IBuildingDao;
import com.rot.model.Building;

@Repository
public class BuildingDao implements IBuildingDao {

	private String ns = "building.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int createBuilding(Building building) {
		return sqlSession.insert(ns + "createBuilding", building);
	}

	@Override
	public List<Building> getAllBuilding() {
		return sqlSession.selectList(ns + "getAllBuilding");
	}

	@Override
	public long getRecentNum(Building building) {
		return sqlSession.selectOne(ns + "getRecentNum", building);
	}

	@Override
	public List<Building> getBuildingNum(Building building) {
		return sqlSession.selectList(ns + "getBuildingNum", building);
	}

	@Override
	public List<Building> getAgentContribution(String license) {
		return sqlSession.selectList(ns + "getAgentContribution", license);
	}

	@Override
	public Building getBuildingByNum(long num) {
		return sqlSession.selectOne(ns + "getBuildingByNum", num);
	}

	@Override
	public List<Building> getDetailList(Building tempBuilding) {
		return sqlSession.selectList(ns + "getDetailList", tempBuilding);
	}

	@Override
	public String getBImage(Building building) {
		return sqlSession.selectOne(ns + "getBImage", building);
	}

	@Override
	public String getMImage(Building building) {
		return sqlSession.selectOne(ns + "getMImage", building);
	}

}
