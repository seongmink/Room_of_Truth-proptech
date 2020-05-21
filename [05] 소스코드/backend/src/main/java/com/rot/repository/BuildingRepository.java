package com.rot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rot.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

//	int createBuilding(Building building);
//
//	List<Building> getAllBuilding();
//
//	long getRecentNum(Building building);
//
//	List<Building> getBuildingNum(Building building);
//
//	List<Building> getAgentContribution(String license);
//
//	Building getBuildingByNum(long num);
//
//	List<Building> getDetailList(Building tempBuilding);
//
//	String getBImage(Building building);
//
//	String getMImage(Building building);
}
