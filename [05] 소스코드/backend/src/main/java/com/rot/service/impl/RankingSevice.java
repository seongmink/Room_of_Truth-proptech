package com.rot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rot.dao.IRankingDao;
import com.rot.model.Ranking;
import com.rot.service.IRankingService;

@Service
public class RankingSevice implements IRankingService {

	@Autowired
	private IRankingDao rankingDao;

	@Override
	public List<Ranking> getRanking() {
		return rankingDao.getRanking();
	}

}
