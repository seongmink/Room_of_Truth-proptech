package com.rot.dao;

import java.util.List;

import com.rot.model.Address;

public interface IAddressDao {

	List<Address> getAddress(String keyword);

}
