package com.rot.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "user")
public class User {
	@Id
	private long num;

	private String nickname;
	private String auth;
	private String phoneNum;
	private String address;
	private String picture;
	private LocalDateTime createdAt;
	private LocalDateTime enteredAt;
}
