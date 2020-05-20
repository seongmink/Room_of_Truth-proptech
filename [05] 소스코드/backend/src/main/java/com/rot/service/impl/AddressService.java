package com.rot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rot.dao.IAddressDao;
import com.rot.model.Address;
import com.rot.service.IAddressService;

@Service
public class AddressService implements IAddressService {

	@Autowired
	private IAddressDao addressDao;

	@Override
	public List<Address> getAddress(String keyword) {
		return addressDao.getAddress(keyword);
	}

}
