package com.rot.fabric;

import java.util.List;

public interface IFabricCCService {

	public FabricRecord query(String num);

	public boolean registerBuildingInfo(FabricRecord fb);

	List<FabricRecord> getBuildingHistory(String Serial);

	public boolean loadChannel();

}