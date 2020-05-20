package com.rot.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rot.dao.IRankingDao;
import com.rot.model.Ranking;

@Repository
public class RankingDao implements IRankingDao {

	private String ns = "ranking.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Ranking> getRanking() {
		return sqlSession.selectList(ns + "getRanking");
	}

}
