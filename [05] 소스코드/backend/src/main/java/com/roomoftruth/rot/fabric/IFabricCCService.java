package com.roomoftruth.rot.fabric;

import java.util.List;

public interface IFabricCCService {

	public FabricContractRecord queryContract(String num);

	public FabricStatusRecord queryStatus(String num);


	public boolean registerBuildingInfo(FabricContractRecord fb);

	List<FabricContractRecord> getBuildingHistory(String Serial);

	public boolean loadChannel();

}