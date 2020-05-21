package com.rot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rot.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

//	List<Address> getAddress(String keyword);
}
