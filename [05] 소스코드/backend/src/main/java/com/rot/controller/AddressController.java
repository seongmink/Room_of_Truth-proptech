package com.rot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rot.model.Address;
import com.rot.service.IAddressService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AddressController {

	@Autowired
	private IAddressService addressService;

	@GetMapping("/address/{keyword}")
	@ApiOperation("주소 ")
	public List<Address> getAddress(@PathVariable String keyword) {

		List<Address> list = null;

		list = addressService.getAddress(keyword);

		return list;
	}

}
