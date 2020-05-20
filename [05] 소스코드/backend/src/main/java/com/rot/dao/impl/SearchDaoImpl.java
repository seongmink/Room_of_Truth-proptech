package com.rot.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rot.dao.ISearchDao;
import com.rot.model.Search;

@Repository
public class SearchDaoImpl implements ISearchDao {

	private String ns = "search.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Search> getAllSearch(long num) {
		return sqlSession.selectList(ns + "getAllSearch", num);
	}

	@Override
	public Search isSearched(Search search) {
		return sqlSession.selectOne(ns + "isSearched", search);
	}

	@Override
	public int search(Search search) {
		return sqlSession.insert(ns + "search", search);
	}
	
	@Override
	public int deleteSearch(Search search) {
		return sqlSession.delete(ns + "deleteSearch", search);
	}

	@Override
	public int updateSearch(Search search) {
		return sqlSession.update(ns + "updateSearch", search);
	}

}
