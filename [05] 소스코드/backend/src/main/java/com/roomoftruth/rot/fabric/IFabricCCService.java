package com.roomoftruth.rot.fabric;

import java.util.List;

public interface IFabricCCService {

	public FabricContractRecord queryContract(String num);

	public FabricStatusRecord queryStatus(String num);

	public boolean registerContract(FabricContractRecord fc);

	public boolean registerStatus(FabricStatusRecord fs);

	public boolean loadChannel();

}