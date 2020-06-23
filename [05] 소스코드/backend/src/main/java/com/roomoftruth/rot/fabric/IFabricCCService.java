package com.roomoftruth.rot.fabric;

public interface IFabricCCService {

	public ContractRecord queryContract(String num);

	public StatusRecord queryStatus(String num);

	public boolean registerContract(ContractRecord fc);

	public boolean registerStatus(StatusRecord fs);

	public boolean loadChannel();

}