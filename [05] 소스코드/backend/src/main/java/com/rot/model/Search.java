package com.rot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "search")
public class Search {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long num;

	private String keyword;

}
